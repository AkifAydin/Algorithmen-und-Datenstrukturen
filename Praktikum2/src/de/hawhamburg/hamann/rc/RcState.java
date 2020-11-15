package de.hawhamburg.hamann.rc;

import de.hawhamburg.hamann.collections.HAWList;
import de.hawhamburg.hamann.collections.HAWListImpl;

import java.util.Arrays;
import java.util.Objects;

/**
 * Die Klasse speichert den Spielstand und berechnet
 * mögliche nachfolgende Züge.
 *
 * Es werden die Anzahl Plätze im Boot (<code>numSeats</code>),
 * die Gesamtzahl und aktuelle Zahl der Monster (<code>totalMonster</code>, <code>numMonster</code> ),
 * die Gesamtzahl und aktuelle Zahl der Kinder  (<code>totalChildren</code>, <code>numChildren</code> ),
 * sowie die aktuelle Flussseite <code>boatSide</code>gespeichert.
 *
 *
 */
public class RcState {
    public static final int STATE_SUCCESS = Integer.MAX_VALUE;
    public static final int STATE_INVALID = -1;
    public static final int STATE_OK      = 1;

    public static final RcState EMPTY = new RcState(0, new int[RiverSide.values().length], new int[RiverSide.values().length], 0, 0, null);

    private final int numSeats;
    private final int totalChildren;
    private final int totalMonster;

    private final int[] numChildren;
    private final int[] numMonster;

    private final RiverSide boatSide;

    public RcState(int numSeats,
                   int[] numChildren,
                   int[] numMonster,
                   int totalChildren,
                   int totalMonster,
                   RiverSide boatSide) {

        this.numSeats = numSeats;
        this.totalChildren = totalChildren;
        this.totalMonster = totalMonster;

        this.numChildren  = numChildren;
        this.numMonster   = numMonster;

        this.boatSide = boatSide;
    }

    /**
     * Berechnet, ob der Zustand gültig ist (<code>STATE_OK</code>, d. h. nicht
     * mehr Monster auf einer Seite sind als Kinder (falls Kinder anwesend).
     * Sollte diese Situation eintreffen, dann wird <code>STATE_INVALID</code> zurückgegeben.
     * Sind alle Kinder und Monster auf der rechten Flußseite (<code>RiverSide.RIGHT</code>)
     * dann ist das Spiel gewonnen (<code>STATE_SUCESS</code>).
     *
     * @return Der <code>int</code>-Wert der aktuellen Lösung.
     */
    public int score() {

        for (RiverSide side : RiverSide.values() ) {
            if (this.numMonster[side.ordinal()] > this.numChildren[side.ordinal()]
                    && this.numChildren[side.ordinal()] > 0) {
                return STATE_INVALID;
            }
        }

        if (this.numChildren[RiverSide.RIGHT.ordinal()] == this.totalChildren &&
                this.numMonster[RiverSide.RIGHT.ordinal()] ==  this.totalMonster) {
            return STATE_SUCCESS;
        }

        return STATE_OK;
    }

    /**
     * Ermittelt die nächste Flussseite
     * @return Die Flussseite des nächsten Schritts
     */
    public RiverSide getNextStop() {
        if (this.boatSide == RiverSide.LEFT) {
            return RiverSide.RIGHT;
        } else {
            return RiverSide.LEFT;
        }
    }

    /**
     * Diese Methode ermittelt die nächsten möglichen Züge bei denen
     * mindestens ein Kind oder Monster transportiert wird.
     *
     * @return Eine <code>HAWList</code> mit allen möglichen Zügen bei denen etwas transportiert wird.
     */
    public HAWList<RcState> getPossibleMoves() {
        HAWList<RcState> moves = new HAWListImpl<>();

        // Maximal können die Plätze im Boot belegt sein. Wenn weniger am Ufer, dann natürlich nur diese...
        int maxLeavingChildren = Math.min(this.numChildren[this.boatSide.ordinal()], numSeats);
        int maxLeavingMonster  = Math.min(this.numMonster[this.boatSide.ordinal()],  numSeats);

        RiverSide newSide = this.getNextStop();

        // Alle möglichen Kombinationen an Monster/Kinder ermitteln
        for(int numChildsToLeave = 0; numChildsToLeave <= maxLeavingChildren; numChildsToLeave++) {
            for (int numMonsterToLeave = 0; numMonsterToLeave <= maxLeavingMonster && numMonsterToLeave + numChildsToLeave <= numSeats; numMonsterToLeave++) {
                // Die "leere"-Aktion lassen wir aus. Wir wollen ja weiterkommen...
                if (numChildsToLeave > 0 || numMonsterToLeave > 0) {
                    RcState possibleMove;

                    int[] numChildren = Arrays.copyOf(this.numChildren, this.numChildren.length);
                    int[] numMonster  = Arrays.copyOf(this.numMonster,  this.numMonster.length);

                    // Aus dem Pott nehmen
                    numChildren[this.boatSide.ordinal()] -= numChildsToLeave;
                    numMonster[this.boatSide.ordinal()] -= numMonsterToLeave;

                    // In den neuen Pott tun
                    numChildren[newSide.ordinal()] += numChildsToLeave;
                    numMonster[newSide.ordinal()] += numMonsterToLeave;

                    possibleMove = new RcState(this.numSeats,
                            numChildren,
                            numMonster,
                            this.totalChildren,
                            this.totalMonster,
                            newSide);

                    moves.add(possibleMove);
                }
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return "RcState{" +
                "Boat is:" + this.boatSide +
                ", LEFT (C/M): " + numChildren[RiverSide.LEFT.ordinal()] +
                "/" + numMonster[RiverSide.LEFT.ordinal()] +
                ", RIGHT: " + numChildren[RiverSide.RIGHT.ordinal()] +
                "/" + numMonster[RiverSide.RIGHT.ordinal()] +
                ",score=" + this.score() +
                '}';
    }

    /**
     * KAnn zur einfachen Ausgabe eines Zuges verwendet werden.
     * Statt Ausgangs- und Endzustand hat man dann "Nimm x,y im Boot mit"
     * @param previousState Der vorherige <code>RcState</code>
     * @return Einen <code>String</code> der den Zug von <code>previousState</code> zu
     * <code>this</code> beschreibt
     */
    public String toStepString(RcState previousState) {
        int numChildren = this.numChildren[RiverSide.LEFT.ordinal()] - previousState.numChildren[RiverSide.LEFT.ordinal()];
        int numMonster  = this.numMonster[RiverSide.LEFT.ordinal()] - previousState.numMonster[RiverSide.LEFT.ordinal()];

        numChildren = Math.abs(numChildren);
        numMonster  = Math.abs(numMonster);

        return String.format("Boat(C:%d,M:%d)", numChildren, numMonster);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RcState rcState = (RcState) o;
        return Arrays.equals(numChildren, rcState.numChildren) &&
                Arrays.equals(numMonster, rcState.numMonster) &&
                boatSide == rcState.boatSide;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(boatSide);
        result = 31 * result + Arrays.hashCode(numChildren);
        result = 31 * result + Arrays.hashCode(numMonster);
        return result;
    }
}

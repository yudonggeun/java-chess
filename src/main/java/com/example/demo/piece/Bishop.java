package com.example.demo.piece;

public class Bishop implements Piece {

    private final Color color;

    public Bishop() {
        this(Color.WHITE);
    }

    public Bishop(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public float getPoint() {
        return 3;
    }

    @Override
    public Type getType() {
        return Type.BISHOP;
    }

    @Override
    public String toString(){
        return switch (color) {
            case WHITE -> "b";
            case BLACK -> "B";
        };
    }

    @Override
    public int compareTo(Piece piece) {
        return Float.compare(this.getPoint(), piece.getPoint());
    }
}

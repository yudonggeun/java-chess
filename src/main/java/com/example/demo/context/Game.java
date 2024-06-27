package com.example.demo.context;

import com.example.demo.piece.Color;
import com.example.demo.piece.Piece;
import com.example.demo.piece.Type;
import com.example.demo.rules.NormalRule;
import com.example.demo.rules.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.context.Board.Location;

public class Game {
    private final Board board;
    private boolean isEnd = false;
    private final Map<Type, List<Rule>> rules = new HashMap<>();

    public Game(Board board) {
        this.board = board;
        initPawnRules();
        initBishopRules();
        initRookRules();
    }

    //--------------init game start----------------
    private void initPawnRules() {
        List<Rule> pawnRules = new ArrayList<>();
        pawnRules.add(new NormalRule(1, 0, Color.WHITE, Type.PAWN, false));
        pawnRules.add(new NormalRule(2, 0, Color.WHITE, Type.PAWN, true));
        pawnRules.add(new NormalRule(-1, 0, Color.BLACK, Type.PAWN, false));
        pawnRules.add(new NormalRule(-2, 0, Color.BLACK, Type.PAWN, true));
        rules.put(Type.PAWN, pawnRules);
    }

    private void initBishopRules() {
        List<Rule> bishopRules = new ArrayList<>();
        bishopRules.add(new NormalRule(7, 7, Color.WHITE, Type.BISHOP, false, true));
        bishopRules.add(new NormalRule(-7, 7, Color.WHITE, Type.BISHOP, false, true));
        bishopRules.add(new NormalRule(7, -7, Color.WHITE, Type.BISHOP, false, true));
        bishopRules.add(new NormalRule(-7, -7, Color.WHITE, Type.BISHOP, false, true));

        bishopRules.add(new NormalRule(7, 7, Color.BLACK, Type.BISHOP, false, true));
        bishopRules.add(new NormalRule(-7, 7, Color.BLACK, Type.BISHOP, false, true));
        bishopRules.add(new NormalRule(7, -7, Color.BLACK, Type.BISHOP, false, true));
        bishopRules.add(new NormalRule(-7, -7, Color.BLACK, Type.BISHOP, false, true));
        rules.put(Type.BISHOP, bishopRules);
    }

    private void initRookRules() {
        List<Rule> rookRules = new ArrayList<>();
        rookRules.add(new NormalRule(7, 0, Color.WHITE, Type.ROOK, false, true));
        rookRules.add(new NormalRule(-7, 0, Color.WHITE, Type.ROOK, false, true));
        rookRules.add(new NormalRule(0, 7, Color.WHITE, Type.ROOK, false, true));
        rookRules.add(new NormalRule(0, -7, Color.WHITE, Type.ROOK, false, true));

        rookRules.add(new NormalRule(7, 0, Color.BLACK, Type.ROOK, false, true));
        rookRules.add(new NormalRule(-7, 0, Color.BLACK, Type.ROOK, false, true));
        rookRules.add(new NormalRule(0, 7, Color.BLACK, Type.ROOK, false, true));
        rookRules.add(new NormalRule(0, -7, Color.BLACK, Type.ROOK, false, true));
        rules.put(Type.ROOK, rookRules);
    }
    //--------------init game end  ----------------

    public void start() {
    }

    public void end() {
        isEnd = true;
    }

    public boolean isEnd() {
        return this.isEnd;
    }

    public void printBoard() {
        System.out.println(board);
    }

    /**
     * from에서 to로 체스의 말을 이동하도록 명령합니다.
     *
     * @param from 이동할 말이 위치한 좌표
     * @param to   이동할 목표 좌표
     * @throws RuntimeException 이동할 수 없는 경우
     */
    public void move(Location from, Location to) {

        Piece piece = board.getPiece(from.rank(), from.file());

        if (!accept(from, to)) {
            throw new RuntimeException("이동할 수 없습니다.");
        }

        board.setPiece(from, null);
        board.setPiece(to, piece);
    }

    /**
     * 구현된 체스 규칙에 맞는지 확인할 때, 사용합니다.
     *
     * @param from 이동할 체스말의 현재 위치를 나타냅니다.
     * @param to   이동할 위치를 나타냅니다.
     * @return 이동이 가능한 경우 true를 반환하고 이동이 불가능한 경우에는 false를 반환합니다.
     */
    public boolean accept(Location from, Location to) {

        Piece piece = board.getPiece(from);

        if (piece == null) {
            return false;
        }

        return rules.getOrDefault(piece.getType(), new ArrayList<>())
                .stream()
                .anyMatch(rule -> rule.allow(from, to, board));
    }
}

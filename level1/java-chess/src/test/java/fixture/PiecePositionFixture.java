package fixture;

import static domain.piece.Color.BLACK;
import static domain.piece.Color.WHITE;
import static fixture.PositionFixture.A7;
import static fixture.PositionFixture.B6;
import static fixture.PositionFixture.B8;
import static fixture.PositionFixture.C7;
import static fixture.PositionFixture.C8;
import static fixture.PositionFixture.D7;
import static fixture.PositionFixture.E1;
import static fixture.PositionFixture.E6;
import static fixture.PositionFixture.F1;
import static fixture.PositionFixture.F2;
import static fixture.PositionFixture.F3;
import static fixture.PositionFixture.F4;
import static fixture.PositionFixture.G2;
import static fixture.PositionFixture.G4;
import static fixture.PositionFixture.H3;

import domain.piece.Piece;
import domain.piece.piecerole.Bishop;
import domain.piece.piecerole.BlackPawn;
import domain.piece.piecerole.King;
import domain.piece.piecerole.Knight;
import domain.piece.piecerole.Queen;
import domain.piece.piecerole.Rook;
import domain.piece.piecerole.WhitePawn;
import domain.position.Position;
import java.util.Map;

public class PiecePositionFixture {

    /*
     * .KR.....  8
     * P.PB....  7
     * .P..Q...  6
     * ........  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * ....rk..  1
     * abcdefgh
     */
    public static final Map<Position, Piece> PIECE_POSITION_FOR_BLACK_WINS = Map.ofEntries(
            Map.entry(B8, new Piece(King.create(), BLACK)),
            Map.entry(C8, new Piece(Rook.create(), BLACK)),
            Map.entry(A7, new Piece(BlackPawn.create(), BLACK)),
            Map.entry(C7, new Piece(BlackPawn.create(), BLACK)),
            Map.entry(D7, new Piece(Bishop.create(), BLACK)),
            Map.entry(B6, new Piece(BlackPawn.create(), BLACK)),
            Map.entry(E6, new Piece(Queen.create(), BLACK)),
            Map.entry(F4, new Piece(Knight.create(), WHITE)),
            Map.entry(G4, new Piece(Queen.create(), WHITE)),
            Map.entry(F3, new Piece(WhitePawn.create(), WHITE)),
            Map.entry(H3, new Piece(WhitePawn.create(), WHITE)),
            Map.entry(F2, new Piece(WhitePawn.create(), WHITE)),
            Map.entry(G2, new Piece(WhitePawn.create(), WHITE)),
            Map.entry(E1, new Piece(Rook.create(), WHITE)),
            Map.entry(F1, new Piece(King.create(), WHITE)
            )
    );

    /*
     * .KR.....  8
     * P.PB....  7
     * .P......  6
     * ........  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * ....rk..  1
     * abcdefgh
     */
    public static final Map<Position, Piece> PIECE_POSITION_FOR_WHITE_WINS = Map.ofEntries(
            Map.entry(B8, new Piece(King.create(), BLACK)),
            Map.entry(C8, new Piece(Rook.create(), BLACK)),
            Map.entry(A7, new Piece(BlackPawn.create(), BLACK)),
            Map.entry(C7, new Piece(BlackPawn.create(), BLACK)),
            Map.entry(D7, new Piece(Bishop.create(), BLACK)),
            Map.entry(B6, new Piece(BlackPawn.create(), BLACK)),
            Map.entry(F4, new Piece(Knight.create(), WHITE)),
            Map.entry(G4, new Piece(Queen.create(), WHITE)),
            Map.entry(F3, new Piece(WhitePawn.create(), WHITE)),
            Map.entry(H3, new Piece(WhitePawn.create(), WHITE)),
            Map.entry(F2, new Piece(WhitePawn.create(), WHITE)),
            Map.entry(G2, new Piece(WhitePawn.create(), WHITE)),
            Map.entry(E1, new Piece(Rook.create(), WHITE)),
            Map.entry(F1, new Piece(King.create(), WHITE)
            )
    );
}

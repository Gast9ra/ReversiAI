import org.junit.jupiter.api.Test;
import reversi.HumanAgent;
import reversi.ReversiController;
import reversi.ReversiGame;

import static org.junit.jupiter.api.Assertions.*;


public class test {
    @Test
    void testGreedy(){
        ReversiGame game=new ReversiGame(new HumanAgent("",100,new ReversiController())
                ,new HumanAgent("",100,new ReversiController())
                ,new ReversiController());
        assertEquals(2,game.greedyUtility(0));

    }
}
package tests.chemistry;

import static org.junit.Assert.assertEquals;

import com.enrico.chemistry.atoms.*;
import com.enrico.chemistry.formulaparser.FormulaParser;
import org.testng.annotations.Test;

public class ParserTest {
    @Test
    public void testParser() {
        String formula = "C,H4";
        FormulaParser parser = new FormulaParser(formula);

        Atom[] atomList = parser.getAtoms();

        assertEquals(CarbonAtom.ATOM_SYMBOL, atomList[0].getSymbol());
        assertEquals(HydrogenAtom.ATOM_SYMBOL, atomList[1].getSymbol());
        assertEquals(HydrogenAtom.ATOM_SYMBOL, atomList[2].getSymbol());
        assertEquals(HydrogenAtom.ATOM_SYMBOL, atomList[3].getSymbol());
        assertEquals(HydrogenAtom.ATOM_SYMBOL, atomList[4].getSymbol());
    }
}

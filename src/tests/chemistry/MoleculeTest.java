package tests.chemistry;

import static org.junit.Assert.assertEquals;

import com.enrico.chemistry.atoms.*;
import com.enrico.chemistry.molecule.Molecule;
import com.enrico.chemistry.molecule.exceptions.IllegalMoleculeException;
import org.testng.annotations.Test;

public class MoleculeTest {
    @Test
    public void testMoleculeShape() {

        // Carbon monoxide.
        OxygenAtom ox1 = new OxygenAtom();
        OxygenAtom ox2 = new OxygenAtom();
        CarbonAtom car = new CarbonAtom();

        Atom[] list = new Atom[3];
        list[0] = car;
        list[1] = ox1;
        list[2] = ox2;

        Molecule molecule = null;

        try {
            molecule = new Molecule(list);
            molecule.calculateShape();
        } catch (IllegalMoleculeException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        assertEquals(Molecule.ShapeEnum.LineShape, molecule.getMoleculeShape());

        // Methane.
        Atom[] list2 = new Atom[5];
        list2[0] = new CarbonAtom();
        list2[1] = new HydrogenAtom();
        list2[2] = new HydrogenAtom();
        list2[3] = new HydrogenAtom();
        list2[4] = new HydrogenAtom();

        try {
            molecule = new Molecule(list2);
            molecule.calculateShape();
        } catch (IllegalMoleculeException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        assertEquals(Molecule.ShapeEnum.SquareShape, molecule.getMoleculeShape());

    }
}

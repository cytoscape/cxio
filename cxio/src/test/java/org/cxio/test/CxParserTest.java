package org.cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cxio.aspects.datamodels.CartesianLayoutElement;
import org.cxio.aspects.datamodels.EdgesElement;
import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentReader;
import org.cxio.util.CxioUtil;
import org.junit.Test;

public class CxParserTest {

    @Test
    public void testEmpty1() throws IOException, NoSuchAlgorithmException {

        final String j = "[]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());

        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        p.reset();
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);

    }

    @Test
    public void testEmpty2() throws IOException, NoSuchAlgorithmException {
        final String j = "[{}]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());

        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        p.reset();
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);

    }

    @Test
    public void test1() throws IOException, NoSuchAlgorithmException {
        final String j = "[{\"key\":\"value\"}]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());

        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        p.reset();
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);

    }

    @Test
    public void test2() throws IOException, NoSuchAlgorithmException {
        final String j = "[{\"nodes_we_ignore\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]}]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());

        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        p.reset();
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);

    }

    @Test
    public void test3() throws IOException, NoSuchAlgorithmException {
        final String j = "[{\"nodes\":[{\"@id\":0},{\"@id\":1},{\"@id\":2},{\"@id\":3}]}]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());

        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        p.reset();
        assertTrue(p.hasNext());
        final List<AspectElement> y = p.getNext();
        assertFalse(y == null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertTrue(y.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", y.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", y.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", y.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", y.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", y.contains(new NodesElement("3")));

    }

    @Test
    public void test4() throws IOException, NoSuchAlgorithmException {
        final String j = "[" + "{\"nodes\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]},"
                + "{\"edges\":[{\"@id\":\"0\",\"s\":\"0\",\"t\":\"1\"},{\"@id\":\"1\",\"s\":\"1\",\"t\":\"2\"}]}" + "]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());
        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test5() throws IOException, NoSuchAlgorithmException {
        final String j = "[" + "{\"key\":\"value\"}," + "{\"nodes\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]}," + "{\"key\":\"value\"},"
                + "{\"edges\":[{\"@id\":\"0\",\"s\":\"0\",\"t\":\"1\"},{\"@id\":\"1\",\"s\":\"1\",\"t\":\"2\"}]}," + "{\"key\":\"value\"}" + "]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());

        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        p.reset();
        p.reset();

        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test6() throws IOException, NoSuchAlgorithmException {
        final String j = "["

        + "{\"nodes\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]}," + "{\"key\":\"value\"},"
                + "{\"edges\":[{\"@id\":\"0\",\"s\":\"0\",\"t\":\"1\"},{\"@id\":\"1\",\"s\":\"1\",\"t\":\"2\"}]}," + "{\"key\":\"value\"}" + "]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());

        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test7() throws IOException, NoSuchAlgorithmException {
        final String j = "[" + "{\"key\":\"value\"}," + "{\"nodes\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]},"

        + "{\"edges\":[{\"@id\":\"0\",\"s\":\"0\",\"t\":\"1\"},{\"@id\":\"1\",\"s\":\"1\",\"t\":\"2\"}]}," + "{\"key\":\"value\"}" + "]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());

        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test8() throws IOException, NoSuchAlgorithmException {
        final String j = "[" + "{\"key\":\"value\"}," + "{\"nodes\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]}," + "{\"key\":\"value\"},"
                + "{\"edges\":[{\"@id\":\"0\",\"s\":\"0\",\"t\":\"1\"},{\"@id\":\"1\",\"s\":\"1\",\"t\":\"2\"}]}" + "]";
        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());
        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgesElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test9() throws IOException, NoSuchAlgorithmException {
        final String j = "["
                + "{\"nodes_we_ignore\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]},"
                + "{\"nodes\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]},"
                + "{\"edges\":[{\"@id\":\"0\",\"s\":\"0\",\"t\":\"1\"},{\"@id\":\"1\",\"s\":\"1\",\"t\":\"2\"}]},"
                + "{\"nodeIdentities\":[{\"@id\":\"ni0\",\"nodes\":\"0\",\"represents\":\"name is zero\"},{\"@id\":\"ni1\",\"node\":\"1\",\"represents\":\"name is one\"}]},"
                + "{\"edgeIdentities\":[{\"@id\":\"ei0\",\"edge\":\"e0\",\"relationship\":\"BEL:INCREASES\"},{\"@id\":\"ei1\",\"edge\":\"e1\",\"relationship\":\"BEL:DECREASES\"}]},"
                + "{\"elementProperties\":[{\"@id\":\"ep0\",\"elementId\":\"0\",\"property\":\"propery zero\",\"value\":\"value is zero\"},{\"@id\":\"ep1\",\"elementId\":\"1\",\"property\":\"propery one\",\"value\":\"value is one\"}]},"
                + "{\"functionTerms\":[{\"@id\":\"ft0\",\"function\":\"functions zero\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]},{\"@id\":\"ft1\",\"function\":\"functions one\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]}]},"
                + "{\"nodes\":[{\"@id\":\"5\"}]}," + "{\"edges\":[{\"@id\":\"2\",\"s\":\"4\",\"t\":\"5\"}]}," + "{\"edges\":[{\"@id\":\"3\",\"s\":\"6\",\"t\":\"7\"}]},"
                + "{\"nodes\":[{\"@id\":\"4\"}]}," + "{\"nodes\":[{\"@id\":\"6\"}]}," + "{\"nodes\":[{\"@id\":\"7\"}]}," + "{\"xyz\":{\"nodes\":\"7\"}}," + "{\"abc\":[{\"nodes\":[1,2]}]},"
                + "{\"nmq\":[{\"nodes\":[{\"a\":[1,2,3]},{\"b\":[4,5,{\"id\":\"y\"}]}]}]}," + "{\"table\":[{\"row 0\":[\"00\",\"10\"],\"row 1\":[\"01\",\"11\"],\"row 2\":[\"02\",\"12\"]}]},"
                + "{\"table\":[{\"row 0\":[\"00\",\"10\" ,\"20\"],\"row 1\":[\"01\",\"11\",\"21\"]}]}," + "{\"cartesianLayout\":[{\"node\":\"0\",\"x\":\"123\",\"y\":\"456\"}]},"
                + "{\"cartesianLayout\":[{\"node\":\"1\",\"x\":\"3\",\"y\":\"4\"},{\"node\":\"2\",\"x\":\"5\",\"y\":\"6\"}]}" + "]";

        final CxReader p = CxReader.createInstance(j, CxioUtil.getAllAvailableAspectFragmentReaders());
        //
        assertTrue(p.hasNext());
        List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodesElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 2);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof EdgesElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodesElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof EdgesElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof EdgesElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodesElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodesElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodesElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof CartesianLayoutElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 2);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof CartesianLayoutElement);
        //
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        //
        p.reset();
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodesElement);
        p.reset();
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodesElement);

    }

    @Test
    public void test10() throws IOException, NoSuchAlgorithmException {
        final String j = "[{\"nodes\":[{\"@id\":\"0\"},{\"@id\":\"1\"},{\"@id\":\"2\"},{\"@id\":\"3\"}]}]";
        final Set<AspectFragmentReader> readers = new HashSet<>();
        readers.add(EdgesFragmentReader.createInstance());
        readers.add(NodesFragmentReader.createInstance());
        final CxReader p = CxReader.createInstance(j, readers);

        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", x.contains(new NodesElement("3")));
        p.reset();
        assertTrue(p.hasNext());
        final List<AspectElement> y = p.getNext();
        assertFalse(y == null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertTrue(y.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", y.get(0) instanceof NodesElement);
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", y.contains(new NodesElement("0")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", y.contains(new NodesElement("1")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", y.contains(new NodesElement("2")));
        assertTrue("failed to get expected " + NodesElement.ASPECT_NAME + " aspect", y.contains(new NodesElement("3")));

    }

}

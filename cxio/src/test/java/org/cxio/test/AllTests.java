package org.cxio.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AnonymousElementRoundTripTest.class, AnonymousElementRoundTripTestE.class, CartesianLayoutFragmentReaderTest.class, CartesianLayoutFragmentReaderTestE.class,
    CartesianLayoutFragmentWriterTest.class, CxParserTest.class, CyGroupsFragmentReaderTest.class, CyGroupsFragmentReaderTestE.class, CyGroupsFragmentWriterTest.class,
    CyVisualPropertiesFragmentReaderTest.class, CyVisualPropertiesFragmentReaderTestE.class, CyVisualPropertiesFragmentWriterTest.class, EdgeAttributesFragmentReaderTest.class,
    EdgeAttributesFragmentReaderTestE.class, EdgeAttributesFragmentWriterTest.class, EdgesFragmentReaderTest.class, EdgesFragmentReaderTestE.class, EdgesFragmentWriterTest.class,
    GroupsFragmentReaderTestE.class, NetworkAttributesTest.class, NodeAttributesFragmentReaderTest.class, NodeAttributesFragmentReaderTestE.class, NodeAttributesFragmentWriterTest.class,
    NodesFragmentReaderTest.class, NodesFragmentWriterTest.class, RoundTripTest.class })
public class AllTests {

}

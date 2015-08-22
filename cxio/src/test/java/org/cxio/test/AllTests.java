package org.cxio.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AnonymousElementRoundTripTest.class, CartesianLayoutFragmentReaderTest.class, CartesianLayoutFragmentWriterTest.class, CxParserTest.class, EdgeAttributesFragmentReaderTest.class,
    EdgeAttributesFragmentWriterTest.class, EdgeElementTest.class, EdgesFragmentReaderTest.class, EdgesFragmentWriterTest.class, NodeAttributesFragmentReaderTest.class,
    NodeAttributesFragmentWriterTest.class, NodeElementTest.class, NodesFragmentReaderTest.class, NodesFragmentWriterTest.class, RoundTripTest.class, TimeStampTests.class } )
public class AllTests {

}

/**

Copyright (C) SYSTAP, LLC 2006-2011.  All rights reserved.

Contact:
     SYSTAP, LLC
     4501 Tower Road
     Greensboro, NC 27410
     licenses@bigdata.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
/*
 * Created on Oct 11, 2011
 */
package com.bigdata.rdf.sparql.ast.eval;

/**
 * Test suite for OPTIONAL groups. Unlike the TCK, this test suite is focused on
 * the semantics of well-formed OPTIONAL groups.
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id$
 */
public class TestOptionals extends AbstractDataDrivenSPARQLTestCase {

    public TestOptionals() {
    }

    public TestOptionals(String name) {
        super(name);
    }

    /**
     * Unit test for a simple optional (one where the statement pattern can
     * be lifted into the parent group).
     * 
     * TODO Variant with FILTER which CAN be lifted.
     */
    public void test_simple_optional_01() throws Exception {

        new TestHelper(
                "simple-optional-01",// testURI
                "simple-optional-01.rq", // queryURI
                "simple-optional-01.ttl", // dataURI
                "simple-optional-01.srx" // resultURI
                ).runTest();
        
    }
    
    /**
     * Unit test for an optional which is too complex to be handled as a simple
     * optional (it uses a filter which can not be lifted since it requires a
     * materialized variable).
     */
    public void test_optional_with_filter() throws Exception {

        new TestHelper(
                "optional-with-filter-01",// testURI
                "optional-with-filter-01.rq", // queryURI
                "optional-with-filter-01.ttl", // dataURI
                "optional-with-filter-01.srx" // resultURI
                ).runTest();
        
    }

    /**
     * The FILTER will always fail. This means that the OPTIONAL group will
     * never produce any solutions. Thus only the original solutions from the
     * statement pattern outside of the optional will be reported as solutions
     * for the query. This tests the correct eventual triggering of the optional
     * hash join at the end of the optional group.
     * <p>
     * Note: If this test fails, then it is likely that the optional hash join
     * was not triggered for its last pass evaluation. For this test to pass the
     * optional hash join needs to be triggered for last pass evaluation even
     * though no solutions reached the optional hash join through normal
     * evaluation (they were all failed by the filter).
     */
    public void test_optional_with_filter_that_fails() throws Exception {

        new TestHelper("optional-with-filter-that-fails-01",// testURI
                "optional-with-filter-that-fails-01.rq", // queryURI
                "optional-with-filter-that-fails-01.ttl", // dataURI
                "optional-with-filter-that-fails-01.srx" // resultURI
        ).runTest();

    }

    /**
     * Unit test for an optional which is too complex to be handled as a simple
     * optional (it involves more than one statement pattern).
     */
    public void test_complex_optional_01() throws Exception {
        
        new TestHelper(
                "complex-optional-01",// testURI
                "complex-optional-01.rq", // queryURI
                "complex-optional-01.ttl", // dataURI
                "complex-optional-01.srx"  // resultURI
//                ,true// laxCardinality
//                ,false // checkOrder
                ).runTest();
        
    }
    
}

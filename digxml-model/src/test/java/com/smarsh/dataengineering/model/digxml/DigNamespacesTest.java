package com.smarsh.dataengineering.model.digxml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigNamespacesTest {
    @Test
    void smokeTestForChanges() {
        // this test exists as a check against any changes made to these values

        // validate local names
        assertEquals("itm", DigNamespaces.ITM.localName());
        assertEquals("itm-types", DigNamespaces.ITM_TYPES.localName());
        assertEquals("itm-policy", DigNamespaces.ITM_POL.localName());
        assertEquals("cmn-adt-base", DigNamespaces.CMN_ADT_BASE.localName());
        assertEquals("cmn-adt-core", DigNamespaces.CMN_ADT_CORE.localName());
        assertEquals("cmn-adt-system", DigNamespaces.CMN_ADT_SYSTEM.localName());

        // validate URIs
        assertEquals("http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0", DigNamespaces.ITM.namespaceURI());
        assertEquals("http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0", DigNamespaces.ITM_TYPES.namespaceURI());
        assertEquals("http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/policy/1.0", DigNamespaces.ITM_POL.namespaceURI());
        assertEquals("http://adt.cmn.xmlns.commons.platform.actiance.com/base/1.0", DigNamespaces.CMN_ADT_BASE.namespaceURI());
        assertEquals("http://adt.cmn.xmlns.commons.platform.actiance.com/core/1.0", DigNamespaces.CMN_ADT_CORE.namespaceURI());
        assertEquals("http://adt.cmn.xmlns.commons.platform.actiance.com/system/1.0", DigNamespaces.CMN_ADT_SYSTEM.namespaceURI());
    }
}
package com.smarsh.dataengineering.model.digxml;

import com.smarsh.dataengineering.conversionsdk.namespace.Namespace;

public class DigNamespaces {

    private DigNamespaces() {
        throw new IllegalStateException("class should not be instantiated");
    }

    public static final Namespace ITM = new Namespace("itm", "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/transcript/1.0");
    public static final Namespace ITM_TYPES = new Namespace("itm-types", "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/types/1.0");

    // currently unused
    @SuppressWarnings("unused")
    public static final Namespace ITM_POL = new Namespace("itm-policy", "http://dig.apc.xmlns.commons.platform.actiance.com/itm/interaction/policy/1.0");
    @SuppressWarnings("unused")
    public static final Namespace CMN_ADT_BASE = new Namespace("cmn-adt-base", "http://adt.cmn.xmlns.commons.platform.actiance.com/base/1.0");
    @SuppressWarnings("unused")
    public static final Namespace CMN_ADT_CORE = new Namespace("cmn-adt-core", "http://adt.cmn.xmlns.commons.platform.actiance.com/core/1.0");
    @SuppressWarnings("unused")
    public static final Namespace CMN_ADT_SYSTEM = new Namespace("cmn-adt-system", "http://adt.cmn.xmlns.commons.platform.actiance.com/system/1.0");
}

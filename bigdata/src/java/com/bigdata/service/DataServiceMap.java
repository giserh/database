/**

The Notice below must appear in each file of the Source Code of any
copy you distribute of the Licensed Product.  Contributors to any
Modifications may add their own copyright notices to identify their
own contributions.

License:

The contents of this file are subject to the CognitiveWeb Open Source
License Version 1.1 (the License).  You may not copy or use this file,
in either source code or executable form, except in compliance with
the License.  You may obtain a copy of the License from

  http://www.CognitiveWeb.org/legal/license/

Software distributed under the License is distributed on an AS IS
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
the License for the specific language governing rights and limitations
under the License.

Copyrights:

Portions created by or assigned to CognitiveWeb are Copyright
(c) 2003-2003 CognitiveWeb.  All Rights Reserved.  Contact
information for CognitiveWeb is available at

  http://www.CognitiveWeb.org

Portions Copyright (c) 2002-2003 Bryan Thompson.

Acknowledgements:

Special thanks to the developers of the Jabber Open Source License 1.0
(JOSL), from which this License was derived.  This License contains
terms that differ from JOSL.

Special thanks to the CognitiveWeb Open Source Contributors for their
suggestions and support of the Cognitive Web.

Modifications:

*/
/*
 * Created on Apr 25, 2007
 */

package com.bigdata.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import net.jini.core.lookup.ServiceID;
import net.jini.core.lookup.ServiceItem;
import net.jini.lookup.LookupCache;
import net.jini.lookup.ServiceDiscoveryEvent;
import net.jini.lookup.ServiceDiscoveryListener;
import net.jini.lookup.ServiceDiscoveryManager;

/**
 * A mapping from {@link ServiceID} to {@link ServiceItem} that is maintained by
 * a suitable {@link ServiceDiscoveryManager} manager.  In order to use this
 * class, you must register it as the {@link ServiceDiscoveryListener} with the
 * {@link ServiceDiscoveryManager}.
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id$
 */
public class DataServiceMap implements ServiceDiscoveryListener {

    public static final transient Logger log = Logger
            .getLogger(DataServiceMap.class);
    
    private Map<ServiceID, ServiceItem> serviceIdMap = new ConcurrentHashMap<ServiceID, ServiceItem>();

    public DataServiceMap() {
        
    }

    /*
     * ServiceDiscoveryListener.
     */

    /**
     * Adds the {@link ServiceItem} to the internal map to support
     * {@link #getServiceByID()}
     * <p>
     * Note: This event is generated by the {@link LookupCache}. There is an
     * event for each {@link DataService} as it joins any registrar in the set
     * of registrars to which the {@link MetadataServer} is listening. The set
     * of distinct joined {@link DataService}s is accessible via the
     * {@link LookupCache}.
     */
    public void serviceAdded(ServiceDiscoveryEvent e) {
        
        log.info("" + e + ", class="
                + e.getPostEventServiceItem().toString());
        
        serviceIdMap.put(e.getPostEventServiceItem().serviceID, e
                .getPostEventServiceItem());
        
    }

    /**
     * NOP.
     */
    public void serviceChanged(ServiceDiscoveryEvent e) {

        log.info(""+e+", class="
                + e.getPostEventServiceItem().toString());
        
        serviceIdMap.put(e.getPostEventServiceItem().serviceID, e
                .getPostEventServiceItem());

    }

    /**
     * NOP.
     */
    public void serviceRemoved(ServiceDiscoveryEvent e) {

        log.info(""+e+", class="
                + e.getPreEventServiceItem().toString());

        serviceIdMap.remove(e.getPreEventServiceItem().serviceID);

    }

    /*
     * Our own API.
     */
    
    /**
     * Resolve the {@link ServiceID} for a {@link DataService} to the cached
     * {@link ServiceItem} for that {@link DataService}.
     * 
     * @param serviceID
     *            The {@link ServiceID} for the {@link DataService}.
     * 
     * @return The cache {@link ServiceItem} for that {@link DataService}.
     */
    public ServiceItem getDataServiceByID(ServiceID serviceID) {
        
        return serviceIdMap.get(serviceID);
        
    }
    
    /**
     * Return the #of {@link DataService}s known to this {@link MetadataServer}.
     * 
     * @return The #of {@link DataService}s in the {@link LookupCache}.
     */
    public int getDataServiceCount() {
        
        return serviceIdMap.size();
        
    }
    
}

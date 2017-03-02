package com.allianz.cka.osgi.playground.tutorial1;

/*
 * Apache Felix OSGi tutorial.
 * from http://felix.apache.org/documentation/tutorials-examples-and-presentations/apache-felix-osgi-tutorial/apache-felix-tutorial-example-1.html
**/

import org.osgi.framework.*;
import org.osgi.service.log.LogService;

/**
 * This class implements a simple bundle that utilizes the OSGi
 * framework's event mechanism to listen for service events. Upon
 * receiving a service event, it prints out the event's details.
 **/
public class Activator implements BundleActivator, ServiceListener
{
    private static LogService LOG;

    /**
     * Implements BundleActivator.start(). Prints
     * a message and adds itself to the bundle context as a service
     * listener.
     * @param context the framework context for the bundle.
     **/
    public void start(BundleContext context)
    {
        ServiceReference ref = context.getServiceReference(LogService.class.getName());
        if (ref != null)
        {
            LOG = (LogService) context.getService(ref);
        }

        LOG.log(LogService.LOG_INFO, "Starting to listen for service events.");
        context.addServiceListener(this);

    }

    /**
     * Implements BundleActivator.stop(). Prints
     * a message and removes itself from the bundle context as a
     * service listener.
     * @param context the framework context for the bundle.
     **/
    public void stop(BundleContext context)
    {
        context.removeServiceListener(this);
        LOG.log(LogService.LOG_INFO, "Stopped listening for service events.");

        // Note: It is not required that we remove the listener here,
        // since the framework will do it automatically anyway.
    }

    /**
     * Implements ServiceListener.serviceChanged().
     * Prints the details of any service event from the framework.
     * @param event the fired service event.
     **/
    public void serviceChanged(ServiceEvent event)
    {
        String[] objectClass = (String[])
                event.getServiceReference().getProperty("objectClass");

        if (event.getType() == ServiceEvent.REGISTERED)
        {
            LOG.log(LogService.LOG_INFO,
                    "Ex1: Service of type " + objectClass[0] + " registered.");
        }
        else if (event.getType() == ServiceEvent.UNREGISTERING)
        {
            LOG.log(LogService.LOG_INFO,
                    "Ex1: Service of type " + objectClass[0] + " unregistered.");
        }
        else if (event.getType() == ServiceEvent.MODIFIED)
        {
            LOG.log(LogService.LOG_INFO,
                    "Ex1: Service of type " + objectClass[0] + " modified.");
        }
    }
}


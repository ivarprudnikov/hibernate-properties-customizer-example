package com.example.hibernatemetadata.integrators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class MyIntegrator
        implements org.hibernate.integrator.spi.Integrator {

    public static final MyIntegrator INSTANCE =
            new MyIntegrator();

    public static final PreUpdateEventListener PREUPDATE_INSTANCE =
            new PreUpdateEventListenerImp();

    private Database database;

    private Metadata metadata;

    public Database getDatabase() {
        return database;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    @Override
    public void integrate(
            Metadata metadata,
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {

        this.database = metadata.getDatabase();
        this.metadata = metadata;

        final EventListenerRegistry eventListenerRegistry =
                serviceRegistry.getService(EventListenerRegistry.class);

        eventListenerRegistry.appendListeners(EventType.PRE_UPDATE, PREUPDATE_INSTANCE);
    }

    @Override
    public void disintegrate(
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {

    }

    public static class PreUpdateEventListenerImp implements PreUpdateEventListener {

        private static Logger logger = LoggerFactory.getLogger(PreUpdateEventListenerImp.class);

        @Override
        public boolean onPreUpdate(PreUpdateEvent e) {
            Object[] oldInstance = e.getOldState();
            Object[] newInstance = e.getState();
            logger.info("PreUpdateEvent, oldInstance {} newInstance {}", oldInstance, newInstance);

            throw new RuntimeException("Boo");
        }
    }
}

package com.example.hibernatemetadata.integrators;

import com.example.hibernatemetadata.entities.Book;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class PreUpdateEventListenerImp implements PreUpdateEventListener {

    private static Logger logger = LoggerFactory.getLogger(PreUpdateEventListenerImp.class);

    @Override
    public boolean onPreUpdate(PreUpdateEvent e) {

        if (e.getEntity() instanceof Book) {
            String[] propertyNames = e.getPersister().getEntityMetamodel().getPropertyNames();
            Object[] oldInstance = e.getOldState();
            Object[] newInstance = e.getState();
            logger.info("PreUpdateEvent, props {} oldInstance {} newInstance {}", propertyNames, oldInstance, newInstance);

            int nameIdx = Arrays.asList(propertyNames).indexOf("name");
            Object oldVal = nameIdx > -1 ? oldInstance[nameIdx] : null;
            Object newVal = nameIdx > -1 ? newInstance[nameIdx] : null;

            boolean nameIsDirty = oldVal == null && newVal != null || oldVal != null && !oldVal.equals(newVal);

            if (nameIsDirty)
                throw new RuntimeException("Boo");
        }

        return false;
    }
}

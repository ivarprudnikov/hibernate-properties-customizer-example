package com.example.hibernatemetadata.integrators;

import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;

public class PreDeleteEventListenerImp implements PreDeleteEventListener {

    @Override
    public boolean onPreDelete(PreDeleteEvent e) {
        throw new RuntimeException("Cannot delete");
    }
}

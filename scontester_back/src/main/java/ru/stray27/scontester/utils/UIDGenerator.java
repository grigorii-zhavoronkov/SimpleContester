package ru.stray27.scontester.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class UIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Random random = new Random(object.hashCode());
        char[] UID = new char[5];
        UID[0] = (char) (random.nextInt(26) + 65);
        UID[1] = (char) (random.nextInt(26) + 65);
        UID[2] = (char) (random.nextInt(26) + 65);
        UID[3] = (char) (random.nextInt(9) + 49);
        UID[4] = (char) (random.nextInt(9) + 49);
        return new String(UID);
    }
}

package com.aceward.usermanagement.dao.common;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * TableGenerator (文字列カラム対応)
 */
public class StringTableGenerator extends TableGenerator {

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry)
      throws MappingException {
      super.configure(new BigDecimalType() , params, serviceRegistry);
    }
  
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
      return super.generate(session, obj).toString();
    }
  
  }

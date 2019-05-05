package br.com.onlinemarket.oximetro;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-11T16:58:14")
@StaticMetamodel(Oximetro.class)
public class Oximetro_ { 

    public static volatile SingularAttribute<Telemetry, Integer> spo2;
    public static volatile SingularAttribute<Telemetry, Calendar> created_at;
    public static volatile SingularAttribute<Telemetry, Long> id;
    public static volatile SingularAttribute<Telemetry, String> deviceName;
    public static volatile SingularAttribute<Telemetry, Float> bpm;

}
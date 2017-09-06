package ro.ucv.ace;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleEventAggregator implements EventAggregator {

    private Class<Subscriber> subscriberClass = Subscriber.class;

    private Method method = subscriberClass.getDeclaredMethods()[0];
    ;

    private Map<Type, List<WeakReference<? extends Subscriber>>> map = new HashMap<>();

    public SimpleEventAggregator() {
    }

    public <S extends Subscriber> void subscribe(S subscriber) {

        Type[] interfaces = subscriber.getClass().getGenericInterfaces();
        Type subscriberType = subscriberClass;

        for (Type interfaceType : interfaces) {
            ParameterizedType parameterizedType = (ParameterizedType) interfaceType;
            if (subscriberType.equals(parameterizedType.getRawType())) {
                Type eventType = parameterizedType.getActualTypeArguments()[0];
                System.out.println(eventType);

                List<WeakReference<? extends Subscriber>> subscribers = map.get(eventType);

                if (subscribers == null) {
                    List<WeakReference<? extends Subscriber>> newList = new ArrayList<>();
                    newList.add(new WeakReference<>(subscriber));
                    map.put(eventType, newList);
                } else {
                    subscribers.add(new WeakReference<>(subscriber));
                }
                break;
            }
        }
    }

    public <E> void publish(E e) {
        Type type = e.getClass();
        List<WeakReference<? extends Subscriber>> toRemove = new ArrayList<>();
        List<WeakReference<? extends  Subscriber>> weakReferences = map.get(type);
        if (weakReferences != null) {
            for (WeakReference<? extends Subscriber> weakReference : weakReferences) {
                Subscriber subscriber = weakReference.get();
                if (subscriber != null) {
                    try {
                        method.invoke(subscriber, e);
                    } catch (IllegalAccessException | InvocationTargetException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    toRemove.add(weakReference);
                }
            }

            weakReferences.removeAll(toRemove);
        }

    }
}

package com.ly;

import com.ly.entity.Good;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProtoStuffUtil {
    private static final Map<Class<?>,Schema<?>> SCHEMA_MAP = new ConcurrentHashMap<>();

    private static final Class<SerializeDeserializeWrapperObj> SERIALIZE_DESERIALIZE_WRAPPER_OBJ_CLASS =
            SerializeDeserializeWrapperObj.class;

    private static final Schema<SerializeDeserializeWrapperObj> WRAPPER_SCHEMA =
            RuntimeSchema.createFrom(SERIALIZE_DESERIALIZE_WRAPPER_OBJ_CLASS);

    private static final Set<Class<?>> WRAPPER_CLASS_SET  = new HashSet<>();

    static{
        WRAPPER_CLASS_SET.add(List.class);
        WRAPPER_CLASS_SET.add(Integer.class);
        WRAPPER_CLASS_SET.add(Boolean.class);
        WRAPPER_CLASS_SET.add(Character.class);
        WRAPPER_CLASS_SET.add(Double.class);
        WRAPPER_CLASS_SET.add(int.class);
        WRAPPER_CLASS_SET.add(boolean.class);
        WRAPPER_CLASS_SET.add(char.class);
        WRAPPER_CLASS_SET.add(double.class);
        WRAPPER_CLASS_SET.add(ArrayList.class);
        WRAPPER_CLASS_SET.add(Set.class);
        WRAPPER_CLASS_SET.add(Map.class);
        WRAPPER_CLASS_SET.add(HashMap.class);
        WRAPPER_CLASS_SET.add(Date.class);

    }

    public static  <T> byte[] serializer(T o){

        if(WRAPPER_CLASS_SET.contains(o.getClass())){
            return ProtostuffIOUtil.toByteArray(SerializeDeserializeWrapperObj.builder(o),WRAPPER_SCHEMA, LinkedBuffer.allocate(1024));
        }else{
            return ProtostuffIOUtil.toByteArray(o,getSchema(o.getClass()), LinkedBuffer.allocate(1024));
        }
    }

    public static  <T> byte[] serializerV1(T o){
        Schema<T> schema = getSchema(o.getClass());

        return ProtostuffIOUtil.toByteArray(o,schema, LinkedBuffer.allocate(1024));
    }

    public static <T> Schema getSchema(Class<T> clazz){
        if(SCHEMA_MAP.containsKey(clazz)){
            return SCHEMA_MAP.get(clazz);
        }else{
            Schema<T> schema = RuntimeSchema.createFrom(clazz);
            SCHEMA_MAP.put(clazz,schema);
            return schema;
        }
    }

    public static <T> T deserializer(byte[] bytes,Class<T> clazz)  {


        if(WRAPPER_CLASS_SET.contains(clazz)){
            SerializeDeserializeWrapperObj<T> obj = new SerializeDeserializeWrapperObj<>();
            ProtostuffIOUtil.mergeFrom(bytes, obj, WRAPPER_SCHEMA);
            return obj.getData();
        }else{
            Schema<T> schema = getSchema(clazz);
            T obj = null;
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
            return obj;
        }
    }

    public static Object deserializerToObj(byte[] bytes,Class clazz)  {
        if(WRAPPER_CLASS_SET.contains(clazz)){
            SerializeDeserializeWrapperObj obj = new SerializeDeserializeWrapperObj<>();
            ProtostuffIOUtil.mergeFrom(bytes, obj, WRAPPER_SCHEMA);
            return obj.getData();
        }else{
            Schema schema = RuntimeSchema.createFrom(clazz);
            Object obj = null;
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                return null;
            }
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
            return obj;
        }
    }

    public static <T> T deserializerV1(byte[] bytes,Class<T> clazz) throws IllegalAccessException, InstantiationException {
        Schema<T> schema = getSchema(clazz);
        T obj = clazz.newInstance();
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;

    }

    private static class SerializeDeserializeWrapperObj<T> {

        private T data;

        public static <T> SerializeDeserializeWrapperObj<T> builder(T data) {
            SerializeDeserializeWrapperObj<T> wrapper = new SerializeDeserializeWrapperObj<>();
            wrapper.setData(data);
            return wrapper;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }



    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
        //Good good = Good.builder().gname("fruit").id(1).price(10.0).build();
        /*String abc = "abc";
        byte[] bytes = serializer(good);
        Good temp = deserializer(bytes,Good.class);
        System.out.println(temp.toString());
        System.out.println(good.toString());
        System.out.println(temp.equals(good));*/

        /*Good good1 = new Good();
        good1.setGname("milk");


       List<Good> list= new ArrayList<>();
       list.add(good);
        list.add(good1);*/
        /*byte[] bytes1 = serializer(list);
        System.out.println(deserializer(bytes1,List.class));


        *//*Date date = new Date();
        byte[] bytes = serializer(date);
        System.out.println(deserializer(bytes,Date.class));*//*

        Map<String,String> map = new HashMap<>();
        map.put("1","123");
        map.put("2","2");*/
        /*byte[] bytes2 = serializer(good);
        FileOutputStream outputStream = new FileOutputStream("temp.txt");
        outputStream.write(bytes2);
        outputStream.flush();
        outputStream.close();
        System.out.println(deserializer(bytes2,Good.class));

        System.out.println(Arrays.toString(bytes2));*/
        //MessageMapSchema
        FileInputStream inputStream = new FileInputStream("temp.txt");
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        System.out.println(Arrays.toString(bytes));
        /*FileOutputStream outputStream = new FileOutputStream("temp.txt");
        outputStream.write(bytes2);
        outputStream.flush();
        outputStream.close();*/
        System.out.println(deserializer(bytes, Good.class));




    }


}





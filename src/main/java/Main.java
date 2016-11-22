import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by wangjinzhao on 2016/11/21.
 */
public class Main {

    public static void main(String[] args)
    {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        //2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        try(Session session = sessionFactory.openSession()) {//从会话工厂获取一个session
            session.createNativeQuery("select * from user", User.class)
                    .stream()
                    .filter(t -> t.getAge() > 15)
                    .map(User::toString)
                    .forEach(System.out::println);
        }
    }

}

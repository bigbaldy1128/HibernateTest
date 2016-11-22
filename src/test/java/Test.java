import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by wangjinzhao on 2016/11/22.
 */
public class Test {
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    //2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
    private static final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    @org.junit.Test
    public void search()
    {
        try(Session session = sessionFactory.openSession()) {//从会话工厂获取一个session
            List<User> result = session.createNativeQuery("select * from user", User.class)
                    .stream()
                    .filter(t -> t.getAge() > 15)
                    .collect(Collectors.toList());
            assertEquals("changchuan,45,123",result.get(0).toString());
            assertEquals("liyang,25,123",result.get(1).toString());
        }
    }

    @org.junit.Test
    public void add()
    {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();//开启一个新的事务
            User user = new User();
            user.setName("liyang");
            user.setPassword("123");
            user.setAge(25);
            session.save(user);
            transaction.commit();//提交事务
        }
    }
}

package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.List;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
/*@DirtiesContext*/
public class UserDaoTest {
	@Autowired
	ApplicationContext context;
	
	private UserDao dao; 
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
		this.dao = this.context.getBean("userDao", UserDao.class);
		
		this.user1 = new User("gyumee", "홍길동", "springno1");
		this.user2 = new User("leegw700", "강감찬", "springno2");
		this.user3 = new User("bumjin", "이순신", "springno3");

	}
	
	@Test 
	public void andAndGet() throws SQLException {		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
	}

	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
	}
	
	@Test public void getAll() throws SQLException {
		dao.deleteAll();
		
		Collection<User> users0 = dao.getAll();
		assertThat(users0.size(), is(0));
		
		dao.add(user1);
		Collection<User> users1 = dao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, (User)users1.toArray()[0]);
		
		dao.add(user2);
		Collection<User> users2 = dao.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user1, (User)users2.toArray().clone()[0]);
		checkSameUser(user2, (User)users2.toArray().clone()[1]);
		
		dao.add(user3);
		Collection<User> users3 = dao.getAll();
		assertThat(users3.size(), is(3));
		checkSameUser(user3, (User)users3.toArray()[0]);
		checkSameUser(user1, (User)users3.toArray()[1]);
		checkSameUser(user2, (User)users3.toArray()[2]);
		
	}
	
	@Test
	public void count() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
				
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	private void checkSameUser(User user1, User user2) {
		System.out.println(user1.getId()+":"+user2.getId());
		System.out.println(user1.getName()+":"+user2.getName());
		System.out.println(user1.getPassword()+":"+user2.getPassword());
		System.out.println("-------------------");
		
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
	}
}

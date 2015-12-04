package springbook.user.dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.User;

public class UserDao {
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> userMapper = 
			new RowMapper<User>() {
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					return user;
				}
			};	
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void add(final User user) throws SQLException {
		
		final String query = "insert into users(id, name, password) values(?,?,?)";
		this.jdbcTemplate.update(query, user.getId(),user.getName(),user.getPassword());
	}

	public void deleteAll() throws SQLException {
		
		final String query = "delete from users";
		this.jdbcTemplate.update(query);
	}

	public User get(String id) throws SQLException {
		final String query = "select * from users where id = ?";		
		return this.jdbcTemplate.queryForObject(query, new Object[]{id},this.userMapper);
	}

	public int getCount() throws SQLException {
		
		final String query = "select count(*) from users";
		return this.jdbcTemplate.queryForObject(query, Integer.class);

	}

	public Collection<User> getAll() {
		final String query = "select * from users order by id";
		return jdbcTemplate.query(query, this.userMapper);
	}
	

}


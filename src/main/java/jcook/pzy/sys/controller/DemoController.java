package jcook.pzy.sys.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.apache.catalina.manager.host.HostManagerServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@RestController
@SpringBootApplication
@ComponentScan({"com.pzy.jcook.config.*"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})

public class DemoController {

	@RequestMapping("/")
	String index(){
		return "hello";
	}
	
	 @Bean
	    public FilterRegistrationBean filterRegistrationBean() {
	        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	        filterRegistrationBean.setFilter(new WebStatFilter());
	        filterRegistrationBean.addUrlPatterns("/*");
	        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
	        return filterRegistrationBean;
	    }
	    
	    @Bean
	    public ServletRegistrationBean druidServlet() {
	    	 ServletRegistrationBean reg = new ServletRegistrationBean();
	         reg.setServlet(new StatViewServlet());
	         reg.addUrlMappings("/druid/index");
	         //reg.addInitParameter("allow", "127.0.0.1");
	         //reg.addInitParameter("deny","");
	         reg.addInitParameter("loginUsername", "admin");
	         reg.addInitParameter("loginPassword", "admin");
	         return reg;
	    }
	    
	    @Bean
	    public DataSource druidDataSource(@Value("${spring.datasource.driverClassName}") String driver,
	                                      @Value("${spring.datasource.url}") String url,
	                                      @Value("${spring.datasource.username}") String username,
	                                      @Value("${spring.datasource.password}") String password) {
	        DruidDataSource druidDataSource = new DruidDataSource();
	        druidDataSource.setDriverClassName(driver);
	        druidDataSource.setUrl(url);
	        druidDataSource.setUsername(username);
	        druidDataSource.setPassword(password);
	        try {
	            druidDataSource.setFilters("stat, wall");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return druidDataSource;
	    }
	
	  @Bean
	    public ServletRegistrationBean servletRegistrationBean() {
	        return new ServletRegistrationBean(new  HttpRequestHandlerServlet(), "/xs/*");// ServletName默认值为首字母小写，即myServlet
	    }
	public static void main(String arg[]){
		SpringApplication.run(DemoController.class, arg);
	}
}

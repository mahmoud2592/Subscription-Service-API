package com.subscriptionAPI.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Data
@AllArgsConstructor
@Entity
@Table(name="customers")
public class Customer implements Serializable {
	private static final long serialVersionUID = 5926468583005150707L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "username")
	private String username;
	@Column(name = "first_name")
	private String first_name;
	@Column(name = "last_name")
	private String last_name;
	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private String phone;
	@Column(name = "password")
	private String password;
	@Column(name = "balance")
	private Double balance;
	@Column(name = "is_admin")
	private boolean is_admin;
	@Column(name = "last_login")
	private Timestamp last_login;
	@Column(name = "created_at")
	private Timestamp created_at;
	@Column(name = "is_deleted")
	private boolean is_deleted;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Invoice> invoices = new ArrayList<>();

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Card> cards = new ArrayList<>();

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Plan> plans = new ArrayList<>();


	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "customers_subscriptions",
			joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "subscription_id", referencedColumnName = "id"))
	private List<Subscription> subscriptions = new ArrayList<>();

	public Customer(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	//default constructor for JSON Parsing
	public Customer()
	{
	}

	public Customer(String email, String password) {
		this.setEmail(email);
		this.setPassword(password);
	}

	public Customer(Customer signUpRequest) {
		this.username = signUpRequest.username;
		this.first_name = signUpRequest.first_name;
		this.last_name = signUpRequest.last_name;
		this.email = signUpRequest.email;
		this.password = signUpRequest.password;
		this.phone = signUpRequest.phone;
		this.balance = signUpRequest.balance;
		this.is_admin = signUpRequest.is_admin;
		this.created_at = signUpRequest.created_at;
	}

	public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public boolean isIs_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	public Timestamp getLast_login() {
		return last_login;
	}

	public void setLast_login(Timestamp last_login) {
		this.last_login = last_login;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
}
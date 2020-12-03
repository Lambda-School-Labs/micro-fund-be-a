package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The entity allowing interaction with the users table
 */
@Entity
@Table(name = "users")
public class User
    extends Auditable
{
    /**
     * The primary key (long) of the users table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    /**
     * The username (String). Cannot be null and must be unique
     */
    @NotNull
    @Column(unique = true)
    private String name;

    /**
     * A list of emails for this user
     */
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private List<Useremail> useremails = new ArrayList<>();

    /**
     * Part of the join relationship between user and role
     * connects users to the user role combination
     */
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();

    /**
     * The users address
     */
    private String address;

    /**
     * The users phone
     */
    private String phone;

    /**
     * Image if user provides it
     */
    private String imageUrl;

    /**
     * User description if they provide it
     */
    private String description;

    /**
     * Default constructor used primarily by the JPA.
     */
    public User()
    {
    }

    /**
     * Given the params, create a new user object
     * <p>
     * userid is autogenerated
     *
     * @param name The name (String) of the user
     */
    public User(String name,
                String address,
                String phone,
                String imageUrl,
                String description)
    {
        setName(name);
        setAddress(address);
        setPhone(phone);
        setImageUrl(imageUrl);
        setDescription(description);
    }

    /**
     * Getter for userid
     *
     * @return the userid (long) of the user
     */
    public long getUserid()
    {
        return userid;
    }

    /**
     * Setter for userid. Used primary for seeding data
     *
     * @param userid the new userid (long) of the user
     */
    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    /**
     * Getter for username
     *
     * @return the username (String) lowercase
     */
    public String getName()
    {
        return name;
    }

    /**
     * setter for username
     *
     * @param name the new name (String) converted to lowercase
     */
    public void setName(String name)
    {
        this.name = name.toLowerCase();
    }

    /**
     * Getter for the list of useremails for this user
     *
     * @return the list of useremails (List(Useremail)) for this user
     */
    public List<Useremail> getUseremails()
    {
        return useremails;
    }

    /**
     * Setter for list of useremails for this user
     *
     * @param useremails the new list of useremails (List(Useremail)) for this user
     */
    public void setUseremails(List<Useremail> useremails)
    {
        this.useremails = useremails;
    }

    /**
     * Getter for user role combinations
     *
     * @return A list of user role combinations associated with this user
     */
    public Set<UserRoles> getRoles()
    {
        return roles;
    }

    /**
     * Setter for user role combinations
     *
     * @param roles Change the list of user role combinations associated with this user to this one
     */
    public void setRoles(Set<UserRoles> roles)
    {
        this.roles = roles;
    }

    /**
     * Getter for address
     *
     * @return the address (String)
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * setter for address
     *
     * @param address the new address (String)
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Getter for phone
     *
     * @return the phone (String)
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * setter for phone
     *
     * @param phone the new phone (String)
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * Getter for imageUrl
     *
     * @return the imageUrl (String)
     */
    public String getImageUrl()
    {
        return imageUrl;
    }

    /**
     * setter for imageUrl
     *
     * @param imageUrl the new imageUrl (String)
     */
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    /**
     * Getter for description
     *
     * @return the description (String)
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * setter for description
     *
     * @param description the new description (String)
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Internally, user security requires a list of authorities, roles, that the user has. This method is a simple way to provide those.
     * Note that SimpleGrantedAuthority requests the format ROLE_role name all in capital letters!
     *
     * @return The list of authorities, roles, this user object has
     */
    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.roles)
        {
            String myRole = "ROLE_" + r.getRole()
                .getName()
                .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }
}

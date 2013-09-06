package org.jboss.rhs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Item implements Serializable
{

   @Id
   @GeneratedValue
   private long id;

   private float price;

   @NotNull
   @NotEmpty
   private String name;

   @NotNull
   private String imageAbsolutePath;

   public String getImageAbsolutePath()
   {
      return imageAbsolutePath;
   }

   public void setImageAbsolutePath(String imageAbsolutePath)
   {
      this.imageAbsolutePath = imageAbsolutePath;
   }

   public long getId()
   {
      return id;
   }

   public void setId(long id)
   {
      this.id = id;
   }

   public float getPrice()
   {
      return price;
   }

   public void setPrice(float price)
   {
      this.price = price;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }
}

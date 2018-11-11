package com.example.myfuckingpc.gigshub1.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UserItem {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ImgPath")
    @Expose
    private Object imgPath;
    @SerializedName("IsVerified")
    @Expose
    private Boolean isVerified;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Fullname")
    @Expose
    private Object fullname;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Phonenumber")
    @Expose
    private Object phonenumber;
    @SerializedName("Gender")
    @Expose
    private Object gender;
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("DateOfBirth")
    @Expose
    private Object dateOfBirth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getImgPath() {
        return imgPath;
    }

    public void setImgPath(Object imgPath) {
        this.imgPath = imgPath;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getFullname() {
        return fullname;
    }

    public void setFullname(Object fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Object phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Object dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

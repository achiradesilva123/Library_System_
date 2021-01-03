package com.company.LS.Model;

public class Member {
    private String MemberId;
    private String Name;
    private String Mobile;
    private String Email;

    public Member(String memberId, String name, String mobile, String email) {
        MemberId = memberId;
        Name = name;
        Mobile = mobile;
        Email = email;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

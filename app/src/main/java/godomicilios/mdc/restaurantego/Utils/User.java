package godomicilios.mdc.restaurantego.Utils;

/**
 * Creado por Deimer el 24/08/17.
 */

public class User {

    private int admin_id;
    private int sucursal_id;
    private String identification;
    private String email;
    private String username;
    private String fullname;
    private String telephone;
    private String avatar;
    private int profile_id;
    private String created_at;
    private String token_firebase;

    public User() {}

    public User(int admin_id, int sucursal_id, String identification, String email,
                String username, String fullname, String telephone, String avatar,
                int profile_id, String created_at, String token_firebase) {
        this.admin_id = admin_id;
        this.sucursal_id = sucursal_id;
        this.identification = identification;
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.telephone = telephone;
        this.avatar = avatar;
        this.profile_id = profile_id;
        this.created_at = created_at;
        this.token_firebase = token_firebase;
    }

//region Getters
    public int getAdmin_id() {
        return admin_id;
    }
    public int getSucursal_id() {
        return sucursal_id;
    }
    public String getIdentification() {
        return identification;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getFullname() {
        return fullname;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getAvatar() {
        return avatar;
    }
    public int getProfile_id() {
        return profile_id;
    }
    public String getCreated_at() {
        return created_at;
    }
    public String getToken_firebase() {
        return token_firebase;
    }
//endregion

//region Setters
    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
    public void setSucursal_id(int sucursal_id) {
        this.sucursal_id = sucursal_id;
    }
    public void setIdentification(String identification) {
        this.identification = identification;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public void setToken_firebase(String token_firebase) {
        this.token_firebase = token_firebase;
    }
//endregion

//regionPrinter
    @Override
    public String toString() {
        return "User{" +
                "admin_id=" + admin_id +
                ", sucursal_id=" + sucursal_id +
                ", identification='" + identification + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", profile_id=" + profile_id +
                ", created_at='" + created_at + '\'' +
                ", token_firebase='" + token_firebase + '\'' +
                '}';
    }
//endregion

}

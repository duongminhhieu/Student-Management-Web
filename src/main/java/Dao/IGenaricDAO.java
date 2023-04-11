package Dao;

import java.util.List;

public interface IGenaricDAO<T> {

    // Hàm tìm một đối tượng
    public T find(String id) throws DAOException;

    // lấy ra danh sách
    public List<T> list() throws DAOException;


    // Hàm tạo một đối tượng mới
    public void create(T user) throws IllegalArgumentException, DAOException;

    // update lại đối tượng
    public void update(T user) throws IllegalArgumentException, DAOException;

    // Xóa đối tượng
    public void delete(T user) throws DAOException;

}
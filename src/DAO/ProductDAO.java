package DAO;

import DTO.ProductDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private ConnectManager connectManager;

    public ProductDAO() {
        connectManager = new ConnectManager();
    }

    public boolean addProduct(ProductDTO product) {
        String query = "INSERT INTO SanPham (MaSP, TenSP, MaLoai, MoTa, Gia, SoLuong, HinhAnh, Is_Deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product.getMaSP());
            preparedStatement.setString(2, product.getTenSP());
            preparedStatement.setInt(3, product.getMaLoai());
            preparedStatement.setString(4, product.getMoTa());
            preparedStatement.setDouble(5, product.getGia());
            preparedStatement.setInt(6, product.getSoLuong());
            preparedStatement.setString(7, product.getHinhAnh());
            preparedStatement.setInt(8, product.getIsDeleted());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public boolean updateProduct(ProductDTO product) {
        String query = "UPDATE SanPham SET TenSP = ?, MaLoai = ?, MoTa = ?, Gia = ?, SoLuong = ?, HinhAnh = ?, Is_Deleted = ? WHERE MaSP = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getTenSP());
            preparedStatement.setInt(2, product.getMaLoai());
            preparedStatement.setString(3, product.getMoTa());
            preparedStatement.setDouble(4, product.getGia());
            preparedStatement.setInt(5, product.getSoLuong());
            preparedStatement.setString(6, product.getHinhAnh());
            preparedStatement.setInt(7, product.getIsDeleted());
            preparedStatement.setInt(8, product.getMaSP());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public boolean deleteProduct(int maSP) {
        String query = "UPDATE SanPham SET Is_Deleted = ? WHERE MaSP = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, maSP);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public ProductDTO getProductbyId(int maSP) {
        String query = "SELECT * FROM SanPham WHERE MaSP = ? AND Is_Deleted = false";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, maSP);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new ProductDTO(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaLoai"),
                        rs.getString("MoTa"),
                        rs.getInt("Gia"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("Is_Deleted"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return null;
    }

    public List<ProductDTO> searchProductsByName(String keyword) {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT * FROM SanPham WHERE TenSP LIKE ? AND Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                products.add(new ProductDTO(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaLoai"),
                        rs.getString("MoTa"),
                        rs.getInt("GiaBan"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("Is_Deleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return products;
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT * FROM SanPham WHERE Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                products.add(new ProductDTO(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaLoai"),
                        rs.getString("MoTa"),
                        rs.getInt("GiaBan"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("Is_Deleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return products;
    }
}
package com.tms.dao;

import com.tms.entity.Discipline;
import com.tms.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao extends DbConnection {

    public void save(Teacher teacher) {
        try (PreparedStatement statement = connection.prepareStatement("insert into teachers values (default, ?, ?, ?)")) {
            statement.setString(1, teacher.getName());
            statement.setString(2, teacher.getUsername());
            statement.setString(3, teacher.getPassword());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateNameById(long id, String newName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("update teachers set name = ? where id = ?")) {
            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean existByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement("select count(*) from teachers where username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Teacher findByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement("select * from teachers where username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(4);
                return new Teacher(id, name, username, password);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void addDiscipline(Teacher teacher, Discipline discipline) {
        try (PreparedStatement statement = connection.prepareStatement("insert into td values (?, ?)")) {
            statement.setLong(1, teacher.getId());
            statement.setLong(2, discipline.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from teachers")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                teachers.add(new Teacher(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        getAllDisciplinesByTeacherId(resultSet.getLong(1))));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teachers;
    }

    public List<Discipline> getAllDisciplinesByTeacherId(long id) {
        List<Discipline> disciplines = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT td.d_id, disciplines.name FROM td, disciplines WHERE td.t_id = ? and td.d_id = disciplines.id")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                disciplines.add(new Discipline(resultSet.getLong(1), resultSet.getString(2)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return disciplines;
    }

    public void deleteById(long id) {
        try (PreparedStatement statement = connection.prepareStatement("with t_deleted as (delete from teachers where id =? returning id) delete from td where t_id in (select id from teachers)")){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

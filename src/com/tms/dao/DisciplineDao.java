package com.tms.dao;

import com.tms.entity.Discipline;
import com.tms.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplineDao extends DbConnection{

    public void save(Discipline discipline){
        try (PreparedStatement statement = connection.prepareStatement("insert into disciplines values (default, ? )")){
            statement.setString(1, discipline.getName());
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Discipline getById(long id){
        try (PreparedStatement statement = connection.prepareStatement("select * from disciplines where id = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name  = resultSet.getString(2);
                return new Discipline(id, name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Discipline getByName(String name){
        try (PreparedStatement statement = connection.prepareStatement("select * from disciplines where name = ?")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                return new Discipline(id, name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Discipline> getAll(){
        List<Discipline> disciplines = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from disciplines")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                disciplines.add(new Discipline(resultSet.getLong(1), resultSet.getString(2),
                        getAllTeachersByDisciplineId(resultSet.getLong(1))));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return disciplines;
    }

    public List<Teacher> getAllTeachersByDisciplineId(long id) {
        List<Teacher> teachers = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT td.t_id, t.name, t.username, t.password FROM td, teachers as t WHERE td.d_id = ? and td.t_id = t.id")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                teachers.add(new Teacher(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4),
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
                disciplines.add(new Discipline(resultSet.getLong(1), resultSet.getString(2),
                        getAllTeachersByDisciplineId(resultSet.getLong(1))));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return disciplines;
    }

    public boolean existByName(String name){
        try(PreparedStatement statement = connection.prepareStatement("select count(*) from discipline where name = ?")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void deleteById(long id){
        try(PreparedStatement statement = connection.prepareStatement("with t_deleted as (delete from disciplines where id =? returning id) delete from td where d_id in (select id from disciplines)")) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}

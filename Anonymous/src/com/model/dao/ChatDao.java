package com.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import com.common.JDBCTemplate;
import com.model.vo.Chat;

public class ChatDao {

	private Properties prop = new Properties();

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public ChatDao() {
		String fileName = ChatDao.class.getResource("/sql/chat/chat-query.properties").getPath();
//		System.out.println("fileName :" + fileName);
		try {
			prop.load(new FileReader(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Chat> getChatList(String nowTime) {
		conn = JDBCTemplate.getConnection();

		ArrayList<Chat> chatList = null;

		String sql = prop.getProperty("getChatList");

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nowTime);

			rs = pstmt.executeQuery();

			chatList = new ArrayList<Chat>();

			while (rs.next()) {
				Chat chat = new Chat();
				chat.setChatID(rs.getInt("chatID"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent")
						.replaceAll(" ", "&nbsp;")
						.replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;")
						.replaceAll("\n", "<br>"));
				
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if (Integer.parseInt(rs.getString("chatTime").substring(11, 13)) >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}
				
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) 
						+ " " + timeType + " " + chatTime + ":" 
						+ rs.getString("chatTime").substring(14, 16) 
						+ "");

				chatList.add(chat);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(conn);
		}

		return chatList;
	}

	public ArrayList<Chat> getChatListByRecent(int number) {
		conn = JDBCTemplate.getConnection();

		ArrayList<Chat> chatList = null;

		String sql = prop.getProperty("getChatListByRecent");

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);

			rs = pstmt.executeQuery();

			chatList = new ArrayList<Chat>();

			while (rs.next()) {
				Chat chat = new Chat();
				chat.setChatID(rs.getInt("chatID"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent")
						.replaceAll(" ", "&nbsp;")
						.replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;")
						.replaceAll("\n", "<br>"));
				
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if (Integer.parseInt(rs.getString("chatTime").substring(11, 13)) >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}
				
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) 
						+ " " + timeType + " " + chatTime + ":" 
						+ rs.getString("chatTime").substring(14, 16) 
						+ "");

				chatList.add(chat);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(conn);
		}

		return chatList;
	}

	public ArrayList<Chat> getChatListByChatID(String chatID) {
		conn = JDBCTemplate.getConnection();

		ArrayList<Chat> chatList = null;

		String sql = prop.getProperty("getChatListByChatID");

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(chatID));

			rs = pstmt.executeQuery();

			chatList = new ArrayList<Chat>();

			while (rs.next()) {
				Chat chat = new Chat();
				chat.setChatID(rs.getInt("chatID"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent")
						.replaceAll(" ", "&nbsp;")
						.replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;")
						.replaceAll("\n", "<br>"));
				
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if (Integer.parseInt(rs.getString("chatTime").substring(11, 13)) >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}
				
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) 
						+ " " + timeType + " " + chatTime + ":" 
						+ rs.getString("chatTime").substring(14, 16) 
						+ "");

				chatList.add(chat);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(conn);
		}

		return chatList;
	}
	
	public int submit(String chatName, String chatContent) {
		conn = JDBCTemplate.getConnection();

		String sql = prop.getProperty("submit");

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, chatName);
			pstmt.setNString(2, chatContent);

			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.commit(conn);
			JDBCTemplate.close(conn);
		}

		return -1; // 데이터베이스 오류
	}
}

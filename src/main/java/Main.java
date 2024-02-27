import entities.Member;
import exceptions.DatabaseException;
import exceptions.IllegalInputException;
import persistence.Database;
import persistence.MemberMapper;

import java.sql.SQLException;
import java.util.List;

public class Main {

    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/sportsclub";
    private static MemberMapper memberMapper;
    private static Database db;

    public static void main(String[] args) {

        try {
            Database db = new Database(USER, PASSWORD, URL);
            memberMapper = new MemberMapper(db);
            try {
                List<Member> members = memberMapper.getAllMembers();
                showMembers(members);
                showMemberById(memberMapper, 13);
                int newMemberId = insertMember(memberMapper);
                // deleteMember(newMemberId, memberMapper);
                showMembers(members);
                updateMember(13, memberMapper);
            } catch (DatabaseException | IllegalInputException ex) {
                System.out.println("Problemer med databasen: " + ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("An error has occured: " + ex.getMessage());
            }
        }

        private static void deleteMember(int memberId, MemberMapper memberMapper)  throws DatabaseException {
        if (memberMapper.deleteMember(memberId)){
            System.out.println("Member with id = " + memberId + " is removed from DB");
        }
    }

    private static int insertMember(MemberMapper memberMapper) throws DatabaseException, IllegalInputException {
        Member m1 = new Member("Ole Olsen", "Banegade 2", 3700, "Rønne", "m", 1967);
        Member m2 = memberMapper.insertMember(m1);
        showMemberById(memberMapper, m2.getMemberId());
        return m2.getMemberId();
    }

    private static void updateMember(int memberId, MemberMapper memberMapper) throws DatabaseException {
        Member m1 = memberMapper.getMemberById(memberId);
        m1.setYear(1970);
        if(memberMapper.updateMember(m1)){
            showMemberById(memberMapper, memberId);
        }
    }

    private static void showMemberById(MemberMapper memberMapper, int memberId)  throws DatabaseException {
        System.out.println("***** Vis medlem nr. 13: *******");
        System.out.println(memberMapper.getMemberById(memberId).toString());
    }

    private static void showMembers(List<Member> members) {
        System.out.println("***** Vis alle medlemmer *******");
        for (Member member : members) {
            System.out.println(member.toString());
        }
    }


}

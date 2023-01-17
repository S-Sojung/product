package shop.mtcoding.orange2.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductRepository {

    public List<Product> findAll(); // findAll = select id값

    public Product findOne(int id); //
    // public void addOne(String name, int price, int qty);

    // -1 : DB에러, 1 : 변경된 행이 1건, 0 : 변경된 행이 없다.
    public int insert(@Param("name") String name, @Param("price") int price, @Param("qty") int qty);
    // @Param(" ~~ ") 를 통해 맵핑해줌 : 어느 날... 붙이라고 떴음
    // @RequestParam(" ~~ ") 으로 컨트롤러에서도 썼었다.

    public int delete(@Param("id") int id);

    public int update(@Param("id") int id, @Param("name") String name, @Param("price") int price,
            @Param("qty") int qty);
    // 나중엔 클래스 안에 담아서 클래스로 넘기는 것이 더 좋다.
}

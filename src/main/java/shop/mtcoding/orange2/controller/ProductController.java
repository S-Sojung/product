package shop.mtcoding.orange2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.orange2.model.Product;
import shop.mtcoding.orange2.model.ProductRepository;

@Controller // 파일 리턴, view resolver
public class ProductController {

    @Autowired // Type 으로 찾아냄
    // IoC에는 구현체가 올라가 있기 때문에 재정의된 자식 메서드가 때려짐
    private ProductRepository productRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/redirect")
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException { // 디스패쳐 서블릿
        session.setAttribute("name", "session metacoding");
        // 이름이 같으면? 가장 가까이 있는 메모리에서 찾음 (page> requset> session)
        request.setAttribute("name", "metacoding"); // 모델과 같음 model.addAttribute
        // response.sendRedirect("test.jsp"); //뷰 리졸버 발동X 못찾음
        response.sendRedirect("/test"); // 리다이렉트 하는 명령어
        // requset는 들어온 스트림의 bufferedRead
        // response는 bufferedWrite
    }

    @GetMapping("/dispatcher")
    public void dispatcher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { // 디스패쳐 서블릿
        request.setAttribute("name", "metacoding"); // 모델과 같음
        RequestDispatcher dis = request.getRequestDispatcher("/test");
        dis.forward(request, response);
        // 리퀘스트는 두 번 떴지만, 불러오는게 아닌 리퀘스트가 덮어 씌워지는 것
    }

    @GetMapping({ "/", "/product" }) // 주소 두 개 쓰기
    public String findAll(Model model) { // requset.setAttribute 가 내부적으로 동작한다.
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        // 모델에 데이터를 담아서 뷰에 간다.
        return "product/main"; // request 새로만들어지지만 프레임워크라 덮어씌운다.

    }

    @GetMapping("/product/{id}")
    public String findOne(Model model, @PathVariable int id) { // requset.setAttribute 가 내부적으로 동작한다.
        Product product = productRepository.findOne(id);
        model.addAttribute("product", product);
        return "product/detail";
    }

    @GetMapping("/product/addForm")
    public String addForm() {
        return "product/addForm";
    }

    @PostMapping("/product/add")
    public String add(String name, int price, int qty) {
        int result = productRepository.insert(name, price, qty);
        if (result == 1) {
            System.out.println("성공 여부 : 성공");
            return "redirect:/product";
        } else {
            System.out.println("성공 여부 : 실패");
            return "redirect:/product/addForm";
        }
    }

    @PostMapping("/product/{id}/delete")
    public String delete(@PathVariable int id) {
        int result = productRepository.delete(id);

        if (result == 1) {
            System.out.println("성공 여부 : 성공");
            return "redirect:/";
        } else {
            return "redirect:/product/" + id;
        }
    }

    @GetMapping("/product/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        Product product = productRepository.findOne(id);
        model.addAttribute("product", product);
        return "product/updateForm";
    }

    @PostMapping("/product/{id}/update")
    public String update(@PathVariable int id, String name, int price, int qty) {
        int result = productRepository.update(id, name, price, qty);
        if (result == 1) {
            System.out.println("변경 성공");
            return "redirect:/product/" + id;
        } else {

            return "redirect:/product/" + id + "/updateForm";
        }
    }
}

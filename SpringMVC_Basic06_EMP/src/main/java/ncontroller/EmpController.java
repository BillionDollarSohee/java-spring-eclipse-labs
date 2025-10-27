package ncontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import service.EmpService;
import vo.Emp;

@Controller
@RequestMapping("/emp/")
public class EmpController {

    private EmpService empService;

    @Autowired
    public void setEmpService(EmpService empService) {
        this.empService = empService;
    }

    // 사원 목록 보기
    @RequestMapping("emp.do")
    public String empList(@RequestParam(defaultValue = "1") int page, Model model) {
        List<Emp> list = empService.emplist(page);
        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);
        return "emp/emplist";
    }

    // 사원 상세 보기
    @RequestMapping("empDetail.do")
    public String empDetail(@RequestParam("empno") String empno, Model model) {
        Emp emp = empService.empDetail(empno);
        model.addAttribute("emp", emp);
        return "emp/empDetail";
    }

    // 사원 등록 화면
    @GetMapping("empReg.do")
    public String empRegForm() {
        return "emp/empReg";
    }

    // 사원 등록 처리
    @PostMapping("empReg.do")
    public String empReg(Emp e) {
        return empService.empReg(e);
    }

    // 사원 수정 화면
    @GetMapping("empEdit.do")
    public String empEditForm(@RequestParam("empno") String empno, Model model) {
        Emp emp = empService.empEdit(empno);
        model.addAttribute("emp", emp);
        return "emp/empEdit";
    }

    // 사원 수정 처리
    @PostMapping("empEdit.do")
    public String empEdit(Emp e) {
        return empService.empEdit(e);
    }

    // 사원 삭제
    @GetMapping("empDel.do")
    public String empDel(@RequestParam("empno") String empno) {
        return empService.empDel(empno);
    }
}

package app.search.google.modules.keyword;

import app.search.google.modules.keyword.domain.Search;
import app.search.google.modules.keyword.dto.HostSearchForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping(value = {"/keywords/search"})
    public String searchPage(@RequestParam(value = "searchKeyword", required = false) String searchKeyword, Model model) {
        model.addAttribute("searchKeyword", searchKeyword);

        return "components/keywords/searchPage";
    }

    @PostMapping(value = {"/keywords/search"})
    public String search(@RequestParam(value = "searchKeyword", required = false) String searchKeyword, Model model) throws IOException, JSONException {
        model.addAttribute("results", keywordService.search(searchKeyword));
        model.addAttribute("searchKeyword", searchKeyword);

        return "components/keywords/searchPage";
    }

    @GetMapping(value = {"/keywords/host"})
    public String hostPage(@PageableDefault Pageable pageable, @ModelAttribute HostSearchForm form, Model model) {
        Page<Search> results = keywordService.getHostList(pageable, form);
        model.addAttribute("results", results);
        model.addAttribute("url", "/keywords/host");
        model.addAttribute("sortOption", form.getSortOption());
        model.addAttribute("searchOption", form.getSearchOption());
        model.addAttribute("searchKeyword", form.getSearchKeyword());

        return "components/keywords/hostPage";
    }
}
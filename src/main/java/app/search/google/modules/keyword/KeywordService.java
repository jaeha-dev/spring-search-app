package app.search.google.modules.keyword;

import app.search.google.modules.keyword.domain.Search;
import app.search.google.modules.keyword.domain.SearchOption;
import app.search.google.modules.keyword.domain.SearchResult;
import app.search.google.modules.keyword.dto.HostSearchForm;
import app.search.google.modules.keyword.utility.KeywordHelper;
import app.search.google.infra.utility.PageableHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class KeywordService {

    @Value("${app.url}")
    private String URL; // Google Custom Search API (API 엔드포인트)

    @Value("${app.key}")
    private String KEY; // Google Custom Search API (API 키)

    @Value("${app.cx}")
    private String CX; // Google Custom Search API (검색 엔진 키)

    private final KeywordHelper keywordHelper;
    private final PageableHelper pageableHelper;
    private final SearchRepository searchRepository;
    private final SearchResultRepository searchResultRepository;

    public List<SearchResult> search(String keyword) throws IOException, JSONException {
        String json = Jsoup.connect(URL)
                .data("key", KEY)
                .data("cx", CX)
                .data("q", keyword)
                .ignoreContentType(true)
                .execute().body();

        return store(keyword, json);
    }

    @Transactional
    public List<SearchResult> store(String keyword, String json) throws JSONException {
        List<SearchResult> searchResults = new ArrayList<>();
        JSONArray jsonArray = new JSONObject(json).getJSONArray("items");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject element = jsonArray.getJSONObject(i);
            String url = "";
            String host = "";
            String title = "";
            String content = "";

            if (element.has("link")) {
                // 예시: https://www.rocketpunch.com/companies/dbase
                url = element.get("link").toString();
            }
            if (element.has("displayLink")) {
                // 예시: www.rocketpunch.com
                host = element.get("displayLink").toString();
            }
            if (element.has("title")) {
                // 예시: 디베이스앤 기업, 채용, 투자, 뉴스
                title = element.get("title").toString();
            }
            if (element.has("snippet")) {
                // 예시: CJ의 디지털 광고 사업 부문과 각 분야 디지털 전문가의 역량을 결합시킨...
                content = element.get("snippet").toString().replaceAll("\\R+", "");
            }

            // 중복된 Search 엔터티를 방지하기 위해 기존 엔터티를 조회한다.
            Search search = searchRepository.findByKeywordAndHost(keyword, host).orElse(null);
            // Search 엔터티가 없을 경우, 새 엔터티를 생성한다.
            if (search == null) {
                search = searchRepository.save(Search.builder()
                        .keyword(keyword).host(host)
                        .count(0).hostCount(0)
                        .searchResults(new ArrayList<>()).build());
            }
            // Search 엔터티의 검색 횟수를 1 추가한다.
            search.addCount(1);
            // Search 엔터티의 호스트 이름과 같을 경우, 누적 검색수를 계산한다.
            if (search.getHost().equals(host)) {
                search.addHostCount(keywordHelper.countKeyword(url, host));
            }
            // SearchResult 엔터티를 생성한다.
            SearchResult searchResult = SearchResult
                    .builder()
                    .url(url).title(title).content(content)
                    .frequency(keywordHelper.countKeyword(content, keyword)).build();
            // Search, SearchResult 엔터티의 연관 관계를 맺는다.
            searchResult.setSearch(search);
            // 일괄 처리를 위해 SearchResult 엔터티 리스트에 추가한다.
            searchResults.add(searchResult);
        }
        // 일괄 처리 및 웹 페이지에 출력하기 위해 SearchResult 엔터티 리스트를 반환한다.
        return searchResultRepository.saveAll(searchResults);
    }

    public Page<Search> getHostList(Pageable pageable, HostSearchForm form) {
        Page<Search> searches;
        pageable = pageableHelper.modify(pageable, form.getSortOption().getKey(), form.getSearchOption().getKey());
        String searchKeyword = form.getSearchKeyword();

        if (ObjectUtils.isEmpty(searchKeyword)) {
            searches = searchRepository.findAll(pageable);
        } else {
            switch (form.getSearchOption()) {
                case KEYWORD:
                    searches = searchRepository.findByKeywordLike(pageable, searchKeyword);
                    break;
                case HOST:
                    searches = searchRepository.findByHostLike(pageable, searchKeyword);
                    break;
                default:
                    searches = searchRepository.findAll(pageable);
                    break;
            }
        }

        return searches;
    }
}
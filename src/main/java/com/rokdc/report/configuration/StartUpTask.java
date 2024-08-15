package com.rokdc.report.configuration;


import com.rokdc.report.exception.PropertiesLoadException;
import com.rokdc.report.log.LogWriter;
import com.rokdc.report.model.Page;
import com.rokdc.report.service.PageService;
import com.rokdc.report.utils.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;

@Component
public class StartUpTask implements ApplicationRunner {
    public static final String EXSTENSION = ".json";
    public final LogWriter log;
    public final PageService pageService;
    String mainPage;

    public StartUpTask(
            @Autowired LogWriter log
            , @Autowired PageService pageService
            , @Value("${settings.start-page}") String mainPage
    ) {
        this.log = log;
        this.pageService = pageService;
        this.mainPage = mainPage;
    }

    @Override
    public void run(ApplicationArguments args) throws URISyntaxException, IOException {
        String pathStr = Properties.getPath();

        try {
            pageService.setApp();

            if (hasDuplicatePage(pageService.getApp().getPages())) {
                log.writeError(getClass() + ": В списке страниц есть страницы с неуникальным именем.");
            }

            pageService.fillPageData(pageService.get(mainPage), null);

            log.writeInfo(pageService.getApp().getPages().toString() + " Параметры страниц загружены успешно");

        } catch (PropertiesLoadException e) {

            log.writeError(getClass() + ": Ошибка загрузки файла " + pathStr + ". Файл отсутствует или повреждён");

        }

    }

    private boolean hasDuplicatePage(final List<Page> pages) {
        HashSet<Page> tempSet = new HashSet<Page>();

        final long uniqueCnt = pages.stream().filter(x -> tempSet.add(x)).count();

        return uniqueCnt != pages.size();
    }

}

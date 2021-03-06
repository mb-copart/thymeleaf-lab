package com.peejay.report.module;

import com.peejay.chart.ChartDTO;
import com.peejay.chart.ChartFactory;
import com.peejay.chart.ChartInputDTO;
import com.peejay.report.Module;
import com.peejay.report.domain.ThirdObject;
import com.peejay.report.module.chart.ChartModule;
import com.peejay.report.module.chart.HorizontalBarChartModule;
import com.peejay.report.module.chart.VerticalBarChartModule;
import com.peejay.report.module.table.NaturalTableModule;
import com.peejay.report.module.table.TableModule;
import com.peejay.report.module.text.TextModule;
import com.peejay.report.domain.Repository;
import com.peejay.report.domain.AnotherObject;
import com.peejay.report.domain.SomeObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ModuleFactory {

    @Autowired
    private Repository repository;

    @Autowired
    private ChartFactory chartFactory;

    @Autowired
    private MessageSource messageSource;

    public List<Module> createAllModules() {
        return Arrays.asList(createVerticalBarChartModule(), createHorizontalBarChartModule(), createTextModule(), createNaturalTableModule(), createChartModule(), createTableModule());
    }

    public List<Module> createModuleForKeys(List<String> moduleKeys) {
        List<Module> modules = new ArrayList<Module>();
        for (String moduleKey : moduleKeys) {
            modules.add(createModuleForKey(moduleKey));
        }
        return modules;
    }

    public Module createModuleForKey(String moduleKey) {
        Module module = null;
        if (TextModule.MODULE_KEY.equals(moduleKey)) {
            module = createTextModule();
        } else if (NaturalTableModule.MODULE_KEY.equals(moduleKey)) {
            module = createNaturalTableModule();
        } else if (TableModule.MODULE_KEY.equals(moduleKey)) {
            module = createTableModule();
        } else if (ChartModule.MODULE_KEY.equals(moduleKey)) {
            module = createChartModule();
        } else if (VerticalBarChartModule.MODULE_KEY.equals(moduleKey)) {
            module = createVerticalBarChartModule();
        } else if (HorizontalBarChartModule.MODULE_KEY.equals(moduleKey)) {
            module = createHorizontalBarChartModule();
        }
        return module;
    }

    public Module createTextModule() {
        String text = repository.getText();
        return new TextModule(text);
    }

    public Module createNaturalTableModule() {
        List<ThirdObject> thirdObjects = repository.getThirdObjects();
        return new NaturalTableModule(thirdObjects);
    }

    public Module createTableModule() {
        List<SomeObject> someObjects = repository.getSomeObjects();
        List<AnotherObject> anotherObjects = repository.getAnotherObjects();
        return new TableModule(someObjects, anotherObjects, messageSource);
    }

    public Module createChartModule() {
        return new ChartModule(chartFactory);
    }

    private Module createVerticalBarChartModule() {
        Map<String, Double> inputs1 = new TreeMap<String, Double>();
        inputs1.put("Name 1:1", 10d);
        inputs1.put("Name 1:2", 15d);
        inputs1.put("Name 1:3", 50d);
        inputs1.put("Name 1:4", 15d);
        inputs1.put("Name 1:5", 10d);

        Map<String, Double> inputs2 = new TreeMap<String, Double>();
        inputs2.put("Name 2:1", 5d);
        inputs2.put("Name 2:2", 20d);
        inputs2.put("Name 2:3", 50d);
        inputs2.put("Name 2:4", 20d);
        inputs2.put("Name 2:5", 5d);

        Map<String, Double> inputs3 = new TreeMap<String, Double>();
        inputs3.put("Name 3:1", 15d);
        inputs3.put("Name 3:2", 20d);
        inputs3.put("Name 3:3", 30d);
        inputs3.put("Name 3:4", 20d);
        inputs3.put("Name 3:5", 15d);

        List<Map<String, Double>> inputs = Arrays.asList(inputs1, inputs2, inputs3);
        ChartInputDTO<List<Map<String, Double>>> inputDTO = new ChartInputDTO<List<Map<String, Double>>>(inputs, 600, 250, "png");

        ChartDTO horizontalBarChart = chartFactory.createVerticalBarChart(inputDTO);
        return new HorizontalBarChartModule(horizontalBarChart);
    }

    private Module createHorizontalBarChartModule() {
        Map<String, Double> inputValues = new TreeMap<String, Double>();
        inputValues.put("Name 1", 60d);
        inputValues.put("Name 2", 25d);
        inputValues.put("Name 3", 10d);
        inputValues.put("Name 4", 5d);

        ChartInputDTO<Map<String, Double>> inputDTO = new ChartInputDTO<Map<String, Double>>(inputValues, 600, 250, "png");

        ChartDTO horizontalBarChart = chartFactory.createHorizontalBarChart(inputDTO);
        return new HorizontalBarChartModule(horizontalBarChart);
    }

}

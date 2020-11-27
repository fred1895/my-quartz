package br.com.wod.quartz.resource;


import br.com.wod.quartz.dto.jobinfo.QrtzJobDetailsDTO;
import br.com.wod.quartz.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@CrossOrigin("*")
public class SearchResource {

    @Autowired
    private SearchService service;

    @GetMapping("/by-group")
    @ResponseStatus(HttpStatus.OK)
    public List<QrtzJobDetailsDTO> getByJobGroup(@RequestParam(name = "job_group") String jobGroup) {
        return service.findByGroup(jobGroup);
    }

    @GetMapping("/all-jobs")
    @ResponseStatus(HttpStatus.OK)
    public List<QrtzJobDetailsDTO> allJobs() {
        return service.findAll();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<QrtzJobDetailsDTO> allJobs(
            @RequestParam(name = "status") String status) {
        return service.findByStatus(status);
    }

}

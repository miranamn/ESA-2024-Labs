package com.example.book.controllers;

import com.example.book.dto.AuthorDto;
import com.example.book.dto.GenreDto;
import com.example.book.dto.StoryDto;
import com.example.book.models.Author;
import com.example.book.services.AuthorService;
import com.example.book.services.GenreService;
import com.example.book.services.StoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Controller
@RequestMapping(value = "xsl")
public class XslController {

    private final AuthorService authorService;

    private final GenreService genreService;

    public XslController(AuthorService authorService, GenreService genreService) {
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @ResponseBody
    @GetMapping(path = "/author", produces = MediaType.APPLICATION_XML_VALUE)
    private ModelAndView getAuthor() throws JsonProcessingException {
        Iterable<AuthorDto> list = authorService.getAllAuthors();
        return getModelAndView(list, "authorXSL");
    }

    @ResponseBody
    @GetMapping(path = "/genre", produces = MediaType.APPLICATION_XML_VALUE)
    public ModelAndView getGenre() throws JsonProcessingException {
        Iterable<GenreDto> list = genreService.getAllGenres();
        return getModelAndView(list, "genreXSL");
    }

    private ModelAndView getModelAndView(Iterable<?> list, String viewName) throws JsonProcessingException {
        String str = new XmlMapper().writeValueAsString(list);
        ModelAndView mod = new ModelAndView(viewName);
        Source src = new StreamSource(new StringReader(str));
        mod.addObject("List", src);
        return mod;
    }
}

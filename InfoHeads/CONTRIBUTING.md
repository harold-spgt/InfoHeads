# Contributing
- Follow the Oracle coding conventions. Your code will most likely not even be considered if there are clear issues with it
- Target Java 8 for source and compilation.
- Write complete Javadocs (Or fix mine!!) Do not leave parameters blank and ensure all tags are commented.
- If you tag @Author - this will not result in you being a noted developer on the plugin homepage.
- Make sure the code is efficient. We're not looking for code that does roundabout tasks. Make it short, snappy and well made.
- Test your code. We're not looking for unusable code.

# Checklist

Ready to submit? Perform the checklist below:

Have you met the Oracle conventions?
Have I written proper Javadocs for my public methods? Are the @param and @return fields actually filled out?
Have I git rebased my pull request to the latest commit of the target branch?
Have I combined my commits into a reasonably small number (if not one) commit using git rebase?
Have I made my pull request too large? Pull requests should introduce small sets of changes at a time. Major changes should be discussed with the team prior to starting work.
Are my commit messages descriptive?

# Brackets

This is GOOD:
```
if (var.func(param1, param2)) {
    // do things
}
```
This is EXTREMELY BAD:
```
if(var.func( param1, param2 ))
{
    // do things
}
```

Note: Most of this Contributing text was taken from WorldEdit's Contributing page. However, in no way is this plugin affiliated with WorldEdit and/or it's creators.

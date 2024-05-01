# Git Guidelines

## Branch Structure

We have 2 essential branches, a lot of feature branches and a temporary branch.

- main
- dev
- feature-1
- feature-2
- ...
- hot-fix (sometimes)

## How to use Git? Where should I submit my code?

### feature branch

Every developer creating a new feature should create a new branch from the `dev` branch. The branch name should be brief and directly related to the feature. All code changes must be committed to this branch.

When the feature has been completed, the developer could require to merge feature branch into the `dev` branch. 

### main branch

Once a sufficient number of features have been completed and merged into the `dev` branch, we may consider pushing a release of our project. Following thorough and careful testing, we can then merge the `dev` branch into the `main` branch.

We aim to guarantee that every checkout from the `main` branch results in a usable app.

### dev branch 

New features will initially be merged into this branch before being merged into the `main` branch, to prevent any fatal bugs from affecting the app.

And we can also fully test these code in this branch.

### hot-fix branch

If there is a fatal bug in the `main` branch, we have to fix it immediately. We will create a fix branch from `main` branch and ensure these bugs be resolved as soon as possible and then merge it back to `main` branch and `dev` branch.  

If the bug is discovered in the feature branch, there is no necessary to create this branch. Just fix it and then push it to the feature branch.

## Commit format

every commit should start with a [git-emoji](https://gitmoji.dev/) and the label name, followed with the description of what you do. 

`:emoji: [type name] what I do`

| git-emoji                       | label name  | description example                                        |
|---------------------------------|-------------|------------------------------------------------------------|
| :sparkles: `:sparkles:`         | feat        | user login                                                 |
| :memo: `:memo:`                 | docs        | team meeting                                               |
| :bug: `:bug:`                   | fix         | fix the bug: certain usernames will cause the app to crash |
| :recycle: `:recycle:`           | refactor    | refactor user login module for better performance          |
| :construction: `:construction:` | going       | working on the feature: user login                         |

Please avoid using `going`. This label is useful when you're working on a temporary device and need to save your code before completing all tasks.



package com.darling.easylive.Service;

import com.darling.easylive.Utils.Result;

public interface userAccountService {
    public Result register(String username, String password, String password2);

    public Result login(String username, String password);
}

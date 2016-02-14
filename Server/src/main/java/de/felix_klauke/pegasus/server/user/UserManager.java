/*
 * Copyright 2016 Felix Klauke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.felix_klauke.pegasus.server.user;

import com.google.common.collect.Lists;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import de.felix_klauke.pegasus.server.database.Database;
import io.netty.channel.Channel;
import org.bson.Document;

import java.util.List;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class UserManager {

    private List<User> users;
    private Database userDatabase;

    public UserManager() {
        users = Lists.newArrayList();
        userDatabase = new Database(MongoCredential.createScramSha1Credential("user", "imladria",
                "zetaStar12".toCharArray()), "imladria", "users");
    }

    public User getUser(Channel channel) {
        for (User user : users) {
            if (user.getChannel() == channel) return user;
        }
        return null;
    }

    public boolean authUser(String username, String password) {
        FindIterable<Document> iterable = this.userDatabase.getCollection().find(
                new Document().append("username", username)
        );
        Document document = iterable.first();
        if (document != null) {
            return document.getString("password").equals(password);
        }
        return false;
    }

    public User createUser(String username, Channel channel) {
        User user = new User(channel, username);
        users.add(user);
        return user;
    }

    public List<User> getUsers() {
        return users;
    }

}

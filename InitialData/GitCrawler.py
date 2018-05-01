#!/usr/local/bin/jython
#-*- coding: utf-8 -*-

from subprocess import call #, Popen
import requests
from os import makedirs, walk, remove
from os.path import isdir, join
from pymysql import connect
from time import strftime

def many(cur):
	while True:
		results = cur.fetchmany(10)
		if not results:
			break
		for result in results:
			yield result

import hashlib
def md5(s):
	return hashlib.md5(s).hexdigest()

def read_file(path):
	with open(path, "r") as f:
		return f.read()

class GitCrawler:
	def __init__(self, home="/...Output-Path.../abc"):
		self.base = home
		self.home_path = "%s/%s" % (home, strftime('%Y.%m.%d'))
		self.conn = None

	def start(self):
		if not isdir(self.home_path):
			makedirs(self.home_path)

		#TODO You need to change this host from localhost to a real DB server.
		self.conn = connect(host='203.255.81.42', port=3306, user='root', passwd='sel535424', db='ghtorrent')
		""" Retrieves Github API urls from MySQL Github Archive and check if repo has been forked at least once """
		sql = "SELECT url FROM ghtorrent.projects WHERE language = 'Java' AND forked_from IS NULL AND deleted = 0;"#5001,5000;
		cur = self.conn.cursor()
		cur.execute(sql)

		for row in many(cur):
			url = row[0]
			self.clone_repo(url)

	def is_forked(self, url):
		try:
			res = requests.get(url, auth=('rsirres', '6e0dc35a466c646bda02865a3def298447a5827e'))
			data = res.text.encode("utf-8")
			num_forks = data.split('"forks":')[1].split(",")[0].strip()
			print num_forks != "0"
		except Exception as e:
			print str(e)
			print "Repository is probably unavailable or you reached the limit of your GitHub requests"
			return False

		return num_forks != "0"

	#TODO: Add time and date of download
	def clone_repo(self, url):
		#print url
		project_name = url.split("/")[-1]
		username = url.split("/")[-2]

		new_url = "https://github.com/%s/%s" % (username, project_name)
		project_dir = "%s/%s_%s" % (self.home_path, username, project_name)
		
		if not isdir(project_dir) and self.is_forked(url):
			print "Clone: %s" % url
			makedirs(project_dir)
			call(["git", "clone", new_url], cwd=project_dir)
			self.filter_java_files(project_dir)
		else:
			print "Project: %s already exists." % project_dir

	def filter_java_files(self, directory):
		#from multiprocessing import Process
		from threading import Thread
		def remove_non_java_files(directory):
			non_java_files = (join(dirpath, f)
		    for dirpath, dirnames, files in walk(directory)
		    for f in files if not f.endswith('.java'))
			for non_java_file in non_java_files:
				remove(non_java_file)

		t = Thread(target=remove_non_java_files, args=(directory,))
		t.start()

	def stats(self):
		files = (join(dirpath, f)
		    for dirpath, dirnames, files in walk(self.base)
		    for f in files)
		num_files = 0
		duplicates = 0
		loc = 0

		file_hash_set = set()
		for f in files:
			try:
				f_content = read_file(f)
				h = md5(f_content)

				if h in file_hash_set:
					duplicates += 1
				else:
					file_hash_set.add(h)

				loc += f_content.count('\n')
				num_files += 1
			except:
				pass
		print "Number of files: %s Duplicates: %s, LOC: %s" % (num_files, duplicates, loc)

if __name__ == '__main__':
	crawler = GitCrawler()
	crawler.start()
	crawler.stats()

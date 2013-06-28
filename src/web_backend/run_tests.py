import sys

if __name__ == '__main__':
    import nose
    argv = sys.argv[:]
    #argv.insert(1, '--nocapture')
    nose.main(argv=argv)
